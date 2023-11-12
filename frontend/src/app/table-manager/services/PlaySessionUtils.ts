import {DateUtils} from './DateUtils';
import {PlaySession} from '../model/model';

export class PlaySessionUtils {
  public static validatePlaySession(session: PlaySession): void {
    if (!session.startTime) {
      throw Error('No start time for session');
    }

    if (!session.endTime) {
      throw Error('No end time for session');
    }

    if (session.startTime >= session.endTime) {
      throw Error('End time should be after start time');
    }

    if (!session.duration) {
      throw Error('No duration time for session');
    }

    DateUtils.validateTime(session.duration);

    if (!session.table) {
      throw Error('No table selected for session');
    }

    if (!session.rate) {
      throw Error('No rate selected for session');
    }
  }
}
