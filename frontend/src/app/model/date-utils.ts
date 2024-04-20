import {formatDate} from "@angular/common";

export class DateUtils {
  public static now(): string {
    return formatDate(new Date(), 'yyyy-MM-ddTHH:mm', 'en-US');
  }

  public static nowAsDate(): string {
    return formatDate(new Date(), 'yyyy-MM-dd', 'en-US');
  }
}
