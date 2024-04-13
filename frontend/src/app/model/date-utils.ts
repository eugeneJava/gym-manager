import {formatDate} from "@angular/common";

export class DateUtils {
  public static now(): string {
    return formatDate(new Date(), 'yyyy-MM-ddTHH:mm', 'en-US');
  }
}
