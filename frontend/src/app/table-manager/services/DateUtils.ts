import {Time} from '../model/model';

export const MILLIS_IN_MINUTE = 1000 * 60;
export const MILLIS_IN_HOUR = MILLIS_IN_MINUTE * 60; // 1000 * 60 * 60
export const MILLIS_IN_DAY = MILLIS_IN_HOUR * 24; // 1000 * 60 * 60 * 24

export class DateUtils {

  public static calcualateDiffForDates(greaterDate: Date, lesserDate: Date): Time {
    const distanceInMillis = greaterDate.getTime() - lesserDate.getTime();
    const hours = Math.floor((distanceInMillis / (MILLIS_IN_HOUR))) % 60;
    const minutes = Math.floor((distanceInMillis / (MILLIS_IN_MINUTE))) % 60;
    const seconds = Math.floor((distanceInMillis / 1000)) % 60;
    const time : Time = {
      hours: hours,
      minutes: minutes,
      seconds: seconds
    };
    return time;
  }

  public static addTime(dateString: string, time: Time): string {
    const date = DateUtils.date(dateString);

    date.setHours(date.getHours() + time.hours);
    date.setMinutes(date.getMinutes() + time.minutes);
    date.setSeconds(date.getSeconds() + time.seconds);
    return `${date.getFullYear()}-${date.getMonth()}-${date.getDay()} ${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}`;
  }

  private static toSeconds(time: Time): number {
    let seconds = 0;
    seconds = seconds + time.seconds;
    seconds = seconds + (time.minutes * 60);
    seconds = seconds + (time.hours * 3600);

    return seconds;
  }

  public static addTimes(time1: Time, time2: Time): Time {
    const seconds : number = DateUtils.toSeconds(time1) + DateUtils.toSeconds(time2);
    return DateUtils.secondsToTime(seconds);
  }

  public static calcualateDiff(duration: Time, elapsed: Time): Time {
    const seconds = DateUtils.toSeconds(duration) - DateUtils.toSeconds(elapsed);
    return DateUtils.secondsToTime(seconds);
  }

  public static secondsToTime(seconds: number): Time {
    const time : Time = {
      hours: Math.floor(seconds / 3600) % 60,
      minutes: Math.floor(seconds / 60) % 60,
      seconds: seconds % 60
    };
    return time;
  }

  public static toHours(time: Time): number {
    let hours = 0;
    hours = hours + time.hours;
    hours = hours + time.minutes / 60;
    return hours;
  }

  public static validateTime(time: Time): void {
    if (time.minutes > 59) {
      throw Error('Ivalid minutes: ' + time.minutes);
    }

    if (time.seconds > 59) {
      throw Error('Ivalid seconds: ' + time.seconds);
    }
  }

  public static date(dateString: string): Date {
    if (!dateString) {
      return null;
    }
    const date = new Date(dateString);
    return date;
  }
}
