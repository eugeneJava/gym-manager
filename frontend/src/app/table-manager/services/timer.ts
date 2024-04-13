import {BehaviorSubject, EMPTY, interval, Observable, Subject} from 'rxjs';
import {switchMap, takeUntil} from 'rxjs/operators';
import {Status, Time} from '../model/model';
import {DateUtils} from './DateUtils';


export class Timer {
  private intervalTicker: Observable<number> = interval(1000);
  private timer: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  private stopper: Subject<void> = new Subject();
  public elapsedTime: Time = {} as Time;
  public startTime: Date;
  public endTime: Date;
  public pauseTime: Date;
  private status: Status;
  private timerUpdateSubject: Subject<Time> = new Subject<Time>();

  constructor(startTime: Date, endTime: Date) {
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public start(): void {
    this.timer
      .pipe(
        switchMap(paused => !paused ? this.intervalTicker : EMPTY),
        takeUntil(this.stopper),
      ).subscribe((tick => {
        this.recaluculateElapsedTime(new Date());
      }));
  }

  private recaluculateElapsedTime(currentTime: Date): void {
    const newTime : Time = DateUtils.calcualateDiffForDates(currentTime, this.startTime, false);
    this.elapsedTime = newTime;
    this.timerUpdateSubject.next(this.elapsedTime);
  }

  public stop(): void {
    this.stopper.next();
    this.stopper.complete();
    this.status = Status.FINISHED;
  }

  public pause(): void {
    this.pauseTime = new Date();
    this.timer.next(true);
  }

  public resume(): void {
    const resumeTime = new Date();
    const pauseDuration = resumeTime.getTime() - this.pauseTime.getTime();
    this.endTime = new Date(this.endTime.getTime() + pauseDuration);
    console.log('Pause duration ' + pauseDuration);
    this.timer.next(false);
  }

  public getTimeSubject(): Subject<Time> {
    return this.timerUpdateSubject;
  }

  public updateEndTime(newEndTime: Date): void {
    this.endTime = newEndTime;
  }
}


