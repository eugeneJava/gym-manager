import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {Time} from '../../model/model';
import {NgbTimeStruct} from "@ng-bootstrap/ng-bootstrap";
import {Subject} from "rxjs";
import {takeUntil} from "rxjs/operators";

@Component({
  selector: 'app-duration-picker',
  templateUrl: './duration-picker.component.html',
  styleUrls: ['./duration-picker.component.scss']
})
export class DurationPickerComponent implements OnInit, OnDestroy {

  @Input() predefinedTimeOptions: Time[]
  @Input() initialDuration: Time;
  @Input() duration: FormControl;

  public durationPickerForm: FormGroup;

  private destroy$ = new Subject<void>();

  constructor(private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.durationPickerForm = this.fb.group({
      radioOptionDuration: null,
      ngbTimePickerDuration: null
    });

    this.radioOptionDuration.valueChanges
      .pipe(
        takeUntil(this.destroy$))
      .subscribe(value => {
        this.ngbTimePickerDuration.setValue(this.toNgbTimeStruct(value), {emitEvent: false});

        this.duration.setValue(value);
      });

    this.ngbTimePickerDuration.valueChanges
      .pipe(
        takeUntil(this.destroy$))
      .subscribe((value: NgbTimeStruct) => {
        this.radioOptionDuration.setValue(this.findPredefinedTime(value), {emitEvent: false});

        this.duration.setValue(this.fromNgbTimeStruct(value));
      });

    if (this.initialDuration) {
      this.radioOptionDuration.setValue(this.initialDuration);
    }
  }

  public get ngbTimePickerDuration(): FormControl {
    return this.durationPickerForm.get('ngbTimePickerDuration') as FormControl;
  }

  public get radioOptionDuration(): FormControl {
    return this.durationPickerForm.get('radioOptionDuration') as FormControl;
  }

  private toNgbTimeStruct(time: Time): NgbTimeStruct {
    return {
      hour: time?.hours,
      minute: time?.minutes,
      second: time?.seconds
    }
  }

  public fromNgbTimeStruct(ngbTime: NgbTimeStruct): Time {
    return {
      hours: ngbTime?.hour,
      minutes: ngbTime?.minute,
      seconds: ngbTime?.second
    }
  }

  private findPredefinedTime(ngbTime: NgbTimeStruct): Time {
    if (!ngbTime) {
      return null;
    }
    return this.predefinedTimeOptions.find(time => time.hours === ngbTime.hour && time.minutes === ngbTime.minute);
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
