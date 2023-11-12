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
      hour: hours,
      minute: minutes,
      second: seconds
    };
    return time;
  }

  public static addTime(date: Date, time: Time): Date {
    date.setHours(date.getHours() + time.hour);
    date.setMinutes(date.getMinutes() + time.minute);
    date.setSeconds(date.getSeconds() + time.second);
    return new Date(date.getTime());
  }

  private static toSeconds(time: Time): number {
    let seconds = 0;
    seconds = seconds + time.second;
    seconds = seconds + (time.minute * 60);
    seconds = seconds + (time.hour * 3600);

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
      hour: Math.floor(seconds / 3600) % 60,
      minute: Math.floor(seconds / 60) % 60,
      second: seconds % 60
    };
    return time;
  }

  public static toHours(time: Time): number {
    let hours = 0;
    hours = hours + time.hour;
    hours = hours + time.minute / 60;
    return hours;
  }

  public static validateTime(time: Time): void {
    if (time.minute > 59) {
      throw Error('Ivalid minutes: ' + time.minute);
    }

    if (time.second > 59) {
      throw Error('Ivalid seconds: ' + time.second);
    }
  }
}
