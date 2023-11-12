import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {Times} from '../../services/Times';
import {AddedTime, Time} from '../../model/model';

@Component({
  selector: 'app-add-time-dialog',
  templateUrl: './add-time-dialog.component.html',
  styleUrls: ['./add-time-dialog.component.scss']
})
export class AddTimeDialogComponent implements OnInit {
  public form: FormGroup;
  public Times: typeof Times = Times;
  public times: Time[] = [
    Times.FIFTEEN_MINUTES,
    Times.TWENTY_MINUTES,
    Times.HALF_AN_HOUR,
    Times.FORTY_FIVE_MINUTES,
    Times.ONE_HOUR,
    Times.HOUR_AND_HALF,
    Times.TWO_HOURS];

  constructor(private fb: FormBuilder,
              private activeModal: NgbActiveModal) {
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      duration: null,
      paid: false
    });
  }

  public get duration(): FormControl {
    return this.form.get('duration') as FormControl;
  }

  public save(): void {
    const addedTime : AddedTime = this.form.value as AddedTime;
    this.activeModal.close(addedTime);
  }

  public cancel(): void {
    this.activeModal.dismiss();
  }
}
