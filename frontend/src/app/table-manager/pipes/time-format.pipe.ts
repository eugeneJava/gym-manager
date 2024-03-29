import {Pipe, PipeTransform} from '@angular/core';
import {Time} from '../model/model';

@Pipe({
  name: 'time'
})
export class TimeFormatPipe implements PipeTransform {

  transform(value: Time, ...args: unknown[]): string {
    if (!value) {
      return '';
    }
    return this.pad(value.hours) + ':' + this.pad(value.minutes) + ':' + this.pad(value.seconds);
  }

  private pad(value: number): string {
    if (!value) {
      return '00';
    }

    const valueString : string = value.toString(10);
    if (valueString.length === 1) {
      return '0' + valueString;
    }

    return valueString;
  }

}
