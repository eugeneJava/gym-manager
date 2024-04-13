import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {PlaySessionEditComponent} from '../play-session-edit/play-session-edit.component';
import {ModalService} from '../../services/modal.service';
import {AddTimeDialogComponent} from '../add-time-dialog/add-time-dialog.component';
import {Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';
import {Timer} from '../../services/timer';
import {AddedTime, TableSession, Status, Time, ClientSession} from '../../model/model';
import {DateUtils} from '../../services/DateUtils';
import {TableService} from "../../services/table.service";
import {ClientSessionManager} from "../../services/client.session.manager.service";
import {TableSessionCloseDialogComponent} from "../table-session-close-dialog/table-session-close-dialog.component";

@Component({
  selector: 'app-table-scheduler',
  templateUrl: './table-scheduler.component.html',
  styleUrls: ['./table-scheduler.component.scss']
})
export class TableSchedulerComponent implements OnInit, OnDestroy {
  @Input() public table;
  private timer: Timer;
  public tableSession: TableSession;
  public remainedTime: Time = {} as Time;
  public spentMoney = 0;
  public totalMoney = 0;
  public paidMoney = 0;
  public needToPay = 0;

  @Output() sessionCreated: EventEmitter<TableSession> = new EventEmitter<TableSession>();
  @Output() timeAdded: EventEmitter<AddedTime> = new EventEmitter<AddedTime>();
  @Output() tableChanged: EventEmitter<TableSession> = new EventEmitter<TableSession>();

  private stopper: Subject<void> = new Subject();

  constructor(private fb: FormBuilder,
              private modalService: ModalService,
              private clientSessionManager: ClientSessionManager,
              private tableService: TableService) {
  }

  ngOnInit(): void {
  }

  public startSession(): void {
    this.modalService.show(PlaySessionEditComponent, {
      table: this.table,
      startTime: DateUtils.currentTime()
    }).subscribe((tableSession: TableSession) => {
      this.tableService.startSession(tableSession)
        .subscribe(newSession => this.start(newSession));
    });
  }

  private start(tableSession: TableSession): void {
    this.tableSession = tableSession;
    this.recalculateMoney(this.tableSession);
    this.createAndStartTimer();
    this.clientSessionManager.addTableSession(tableSession);
    this.sessionCreated.emit(this.tableSession);
  }

  public stop(): void {
    this.tableService
      .closeSession(this.tableSession)
      .subscribe(tableSession => {
        this.tableSession = tableSession;
        this.timer.stop();
        this.stopper.next();
      });
  }

  private createAndStartTimer(): void {
    this.timer = new Timer(DateUtils.date(this.tableSession.startDate), DateUtils.date(this.tableSession.endDate));
    this.timer.getTimeSubject()
      .pipe(takeUntil(this.stopper))
      .subscribe((time: Time) => this.timeUpdated(time));
    this.timer.start();
  }

  public timeUpdated(time: Time): void {
    this.tableSession.elapsedTime = time;

    const remaining: Time = DateUtils.calcualateDiff(this.tableSession.duration, this.tableSession.elapsedTime);
    this.remainedTime = remaining;
    this.spentMoney = this.tableSession.rate * DateUtils.toHours(this.tableSession.elapsedTime);
    if (this.remainedTime.seconds === 0 && this.remainedTime.minutes === 0 && this.remainedTime.hours === 0) {
      this.stop();
    }
  }

  private recalculateMoney(playSession: TableSession): void {
    this.totalMoney = this.tableSession.rate * DateUtils.toHours(this.tableSession.duration);
    if (playSession.paid === true) {
      this.paidMoney = this.totalMoney;
    }

    this.needToPay = this.totalMoney - this.paidMoney;
  }

  public closeSession(): void {
    this.tableService
      .getPreCloseCalculations(this.tableSession.clientSessionId)
      .subscribe(tableSessions => {
        this.modalService.show(TableSessionCloseDialogComponent, {
          tableSessions: tableSessions
        }).subscribe((tableSession: TableSession) => {

        });
      });
  }

  public addTime(): void {
    this.tableService.getTableSession(this.tableSession.id)
      .subscribe(tableSession => {
        this.modalService.show(AddTimeDialogComponent, {
          tableSession: tableSession
        }).subscribe((addedTime: AddedTime) => {
          this.tableService.addTime(this.tableSession.id, addedTime)
              .subscribe(updatedSession => {
                  this.tableSession = updatedSession;
                  this.timer.updateEndTime(DateUtils.parseTime(this.tableSession.endDate))
              });
          });
      });
  }

  public moveToAnotherTable(): void {
    this.modalService.show(AddTimeDialogComponent)
      .subscribe((addedTime: AddedTime) => {
        this.startSessionWithAddedTime(addedTime);
      });
  }

  private startSessionWithAddedTime(addedTime: AddedTime): void {
    this.stop();

    const newDuration: Time = DateUtils.addTimes(this.tableSession.duration, addedTime.duration);
    const newEndTime = DateUtils.addTime(this.tableSession.endDate, addedTime.duration);

    const newSession: TableSession = Object.assign(this.tableSession);
    newSession.duration = newDuration;
    newSession.endDate = newEndTime;
    newSession.status = Status.RUNNING;
    this.remainedTime = DateUtils.calcualateDiff(newSession.duration, newSession.elapsedTime);
    this.tableSession = newSession;

    const additionalMoney: number = this.tableSession.rate * DateUtils.toHours(addedTime.duration);
    this.totalMoney = this.totalMoney + additionalMoney;
    if (addedTime.paidAmount === true) {
      this.paidMoney = this.paidMoney + additionalMoney;
    }
    this.needToPay = this.totalMoney - this.paidMoney;

    this.createAndStartTimer();
  }

  public ngOnDestroy(): void {
    this.stopper.next();
    this.stopper.complete();
  }
}
