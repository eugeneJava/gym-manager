import {Component, Input, OnInit} from '@angular/core';
import {Times} from '../../services/Times';
import {AbstractControl} from '@angular/forms';
import {Time} from '../../model/model';

@Component({
  selector: 'app-duration-picker',
  templateUrl: './duration-picker.component.html',
  styleUrls: ['./duration-picker.component.scss']
})
export class DurationPickerComponent implements OnInit {

  @Input() times: Time[]
  @Input() initialDuration: Time;
  @Input() duration: AbstractControl;

  public durationModel: Time = Object.assign(Times.ONE_HOUR);

  constructor() { }

  ngOnInit(): void {
    if (this.initialDuration) {
      this.durationModel = Object.assign(this.initialDuration);
    }

    this.duration.setValue(this.durationModel);
  }

  public setDuration(duration: Time) {
    this.durationModel = duration;
    this.duration.setValue(duration);
  }
}
