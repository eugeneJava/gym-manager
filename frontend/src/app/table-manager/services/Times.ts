import {Time} from '../model/model';

export class Times {

  public static FIFTEEN_MINUTES: Time = {
    hour: 0,
    minute: 15,
    second: 0,
    description: '15 минут'
  };

  public static TWENTY_MINUTES: Time = {
    hour: 0,
    minute: 20,
    second: 0,
    description: '20 минут'
  };

  public static HALF_AN_HOUR: Time = {
    hour: 0,
    minute: 30,
    second: 0,
    description: '30 минут'
  };

  public static  FORTY_FIVE_MINUTES: Time = {
    hour: 0,
    minute: 45,
    second: 0,
    description: '45 минут'
  };

  public static  ONE_HOUR: Time = {
    hour: 1,
    minute: 0,
    second: 0,
    description: 'Час'
  };

  public static HOUR_AND_HALF: Time = {
    hour: 1,
    minute: 30,
    second: 0,
    description: 'Полтора часа'
  };

  public static  TWO_HOURS: Time = {
    hour: 2,
    minute: 0,
    second: 0,
    description: 'Два часа'
  };

}
