import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {PlaySessionEditComponent} from '../play-session-edit/play-session-edit.component';
import {ModalService} from '../../services/modal.service';
import {AddTimeDialogComponent} from '../add-time-dialog/add-time-dialog.component';
import {Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';
import {Timer} from '../../services/timer';
import {AddedTime, PlaySession, Status, Time} from '../../model/model';
import {DateUtils} from '../../services/DateUtils';

@Component({
  selector: 'app-table-scheduler',
  templateUrl: './table-scheduler.component.html',
  styleUrls: ['./table-scheduler.component.scss']
})
export class TableSchedulerComponent implements OnInit, OnDestroy {
  @Input() public table;
  private timer: Timer;
  public playSession: PlaySession;
  public remainedTime: Time = {} as Time;
  public spentMoney = 0;
  public totalMoney = 0;
  public paidMoney = 0;
  public needToPay = 0;

  @Output() sessionCreated: EventEmitter<PlaySession> = new EventEmitter<PlaySession>();
  @Output() timeAdded: EventEmitter<AddedTime> = new EventEmitter<AddedTime>();
  @Output() tableChanged: EventEmitter<PlaySession> = new EventEmitter<PlaySession>();

  private stopper: Subject<void> = new Subject();

  constructor(private fb: FormBuilder,
              private modalService: ModalService) {
  }

  ngOnInit(): void {
  }

  private start(playSession: PlaySession): void {
    this.playSession = playSession;
    const currentTime : Date = new Date();
    const endTime : Date = DateUtils.addTime(new Date(currentTime), this.playSession.duration);

    this.playSession.startTime = currentTime;
    this.playSession.endTime = endTime;
    this.playSession.elapsedTime = {} as Time;
    this.playSession.status = Status.RUNNING;
    this.playSession.table = this.table;
    this.playSession.rate = this.table.rate;
    this.recalculateMoney(this.playSession);

    this.createAndStartTimer();

    this.sessionCreated.emit(playSession);
  }

  private createAndStartTimer(): void {
    this.timer = new Timer(this.playSession.startTime, this.playSession.endTime);
    this.timer.getTimeSubject()
      .pipe(takeUntil(this.stopper))
      .subscribe((time: Time) => this.timeUpdated(time));
    this.timer.start();
  }

  public stop(): void {
    this.timer.stop();
    this.stopper.next();
    this.playSession.status = Status.FINISHED;
    const remaining : Time = DateUtils.calcualateDiff(this.playSession.duration, this.playSession.elapsedTime);

    this.remainedTime = remaining;
  }

  public timeUpdated(time: Time): void {
    this.playSession.elapsedTime = time;

    const remaining : Time = DateUtils.calcualateDiff(this.playSession.duration, this.playSession.elapsedTime);
    this.remainedTime = remaining;
    this.spentMoney = this.playSession.rate * DateUtils.toHours(this.playSession.elapsedTime);
    if (this.remainedTime.second === 0 && this.remainedTime.minute === 0 && this.remainedTime.hour === 0) {
      this.stop();
    }
  }

  private recalculateMoney(playSession: PlaySession): void {
    this.totalMoney = this.playSession.rate * DateUtils.toHours(this.playSession.duration);
    if (playSession.paid === true) {
       this.paidMoney = this.totalMoney;
    }

    this.needToPay = this.totalMoney - this.paidMoney;
  }

  public startSession(): void {
    this.modalService.show(PlaySessionEditComponent, {
      table: this.table
    })
      .subscribe((playSession: PlaySession) => {
        this.start(playSession);
      });
  }

  public addTime(): void {
    this.modalService.show(AddTimeDialogComponent)
      .subscribe((addedTime: AddedTime) => {
        this.startSessionWithAddedTime(addedTime);
        this.timeAdded.emit(addedTime);
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

    const newDuration : Time = DateUtils.addTimes(this.playSession.duration, addedTime.duration);
    const newEndTime : Date = DateUtils.addTime(this.playSession.endTime, addedTime.duration);

    const newSession : PlaySession = Object.assign(this.playSession);
    newSession.duration = newDuration;
    newSession.endTime = newEndTime;
    newSession.status = Status.RUNNING;
    this.remainedTime = DateUtils.calcualateDiff(newSession.duration, newSession.elapsedTime);
    this.playSession = newSession;

    const additionalMoney : number = this.playSession.rate * DateUtils.toHours(addedTime.duration);
    this.totalMoney = this.totalMoney + additionalMoney;
    if (addedTime.paid === true) {
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
