import { Pipe, PipeTransform } from '@angular/core';
import {MoneyUtils} from '../services/MoneyUtils';

@Pipe({
  name: 'money'
})
export class MoneyFormatPipe implements PipeTransform {

  transform(value: number, ...args: unknown[]): number {
    if (!value) {
      return 0;
    }

    return MoneyUtils.round(value);
  }

}
