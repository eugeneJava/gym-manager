import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dateFormat'
})
export class DateFormatPipe implements PipeTransform {

  transform(value: Date, ...args: unknown[]): unknown {
   const monthName = this.getMonthName(value.getMonth() + 1);
    return value.getDate() + ' ' + monthName + ', ' + value.getFullYear() + ' г';
  }

  private getMonthName(monthIndex: number): string {
    switch (monthIndex) {
      case 1:
        return "Января"
      case 2:
        return "Февраля"
      case 3:
        return "Марта"
      case 4:
        return "Апреля"
      case 5:
        return "Мая"
      case 6:
        return "Июня"
      case 7:
        return "Июля"
      case 8:
        return "Августа"
      case 9:
      return "Сентября"
      case 10:
        return "Октября"
      case 11:
        return "Ноябра"
      case 12:
        return "Декабря"

    }
  }
}
