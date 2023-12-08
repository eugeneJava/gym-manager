import {DateUtils} from './DateUtils';
import {TableSession} from '../model/model';

export class PlaySessionUtils {
  public static validatePlaySession(session: TableSession): void {
    if (!session.startDate) {
      throw Error('No start time for session');
    }

    if (!session.endDate) {
      throw Error('No end time for session');
    }

    if (session.startDate >= session.endDate) {
      throw Error('End time should be after start time');
    }

    if (!session.duration) {
      throw Error('No duration time for session');
    }

    DateUtils.validateTime(session.duration);

    if (!session.tableNumber) {
      throw Error('No table selected for session');
    }

    if (!session.rate) {
      throw Error('No rate selected for session');
    }
  }
}
