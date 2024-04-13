import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {TableService} from '../../services/table.service';
import {MoneyUtils} from '../../services/MoneyUtils';
import {Times} from '../../services/Times';
import {Client, TableSession, Status, Table, Time} from '../../model/model';
import {DateUtils} from '../../services/DateUtils';
import {Subject} from "rxjs";
import {takeUntil} from "rxjs/operators";

@Component({
  selector: 'app-play-session-edit',
  templateUrl: './play-session-edit.component.html',
  styleUrls: ['./play-session-edit.component.scss']
})
export class PlaySessionEditComponent implements OnInit, OnDestroy {
  @Input() public playSession: TableSession;
  @Input() public table: Table;
  @Input() public startTime: string;
  public endTime: string;
  public initialDuration: Time = Times.ONE_HOUR;

  public form: FormGroup;
  public tables: Table[];
  public Times: typeof Times = Times;
  public times: Time[] = [Times.HALF_AN_HOUR, Times.FORTY_FIVE_MINUTES, Times.ONE_HOUR, Times.HOUR_AND_HALF, Times.TWO_HOURS];

  private destroy$ = new Subject<void>();


  constructor(private fb: FormBuilder,
              private activeModal: NgbActiveModal,
              private tableService: TableService) {
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      duration: null,
      status: [Status.RUNNING],
      paid: false,
      paidAmount: null,
      tableNumber: null,
      rate: null,
      clients: this.fb.array([]),
    });

    this.endTime = DateUtils.addTimeWithMinutesPercision(this.startTime, this.initialDuration);

    this.tableNumber.valueChanges.pipe(
      takeUntil(this.destroy$))
      .subscribe(tableNumber => {
        if (this.tables) {
          const selectedTable: Table = this.tables.find(table => table.number === tableNumber);
          this.rate.setValue(selectedTable.rate);
        }
      });

    this.duration.valueChanges.pipe(
      takeUntil(this.destroy$))
      .subscribe(duration => {
        this.endTime = DateUtils.addTimeWithMinutesPercision(this.startTime, duration);
      });

    this.paid.valueChanges.pipe(
      takeUntil(this.destroy$))
      .subscribe((paid: boolean) => {
        if (paid) {
          this.paidAmount.setValue(this.getTotalPaymentFormatted());
        } else {
          this.paidAmount.setValue(0);
        }
      });

    if (this.table) {
      this.rate.setValue(this.table.rate);
      this.tableNumber.setValue(this.table.number);
    }

    this.tableService.getTables()
      .subscribe(tables => {
        this.tables = tables;
      });
  }

  public get duration(): FormControl {
    return this.form.get('duration') as FormControl;
  }

  public get paid(): FormControl {
    return this.form.get('paid') as FormControl;
  }

  public get rate(): FormControl {
    return this.form.get('rate') as FormControl;
  }

  public get tableNumber(): FormControl {
    return this.form.get('tableNumber') as FormControl;
  }

  public get paidAmount(): FormControl {
    return this.form.get('paidAmount') as FormControl;
  }

  public get clients(): FormArray {
    return this.form.get('clients') as FormArray;
  }

  public getTotalPayment(): number {
    if (this.rate.value && this.duration.value) {
      return this.rate.value * DateUtils.toHours(this.duration.value);
    }
  }

  public getTotalPaymentFormatted(): number {
    if (this.rate.value && this.duration.value) {
      return MoneyUtils.round(this.rate.value * DateUtils.toHours(this.duration.value));
    }
  }

  public save(): void {
    const playSession: TableSession = this.form.value as TableSession;
    this.activeModal.close(playSession);
  }

  public cancel(): void {
    this.activeModal.dismiss();
  }

  public handleClientSelected(client: Client): void {
    const clients = this.clients.value as Client[];

    if (!clients.some(it => it.phone === client.phone)) {
      this.clients.push(this.fb.control(client));
    }
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
