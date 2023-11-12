import {Component, OnInit} from '@angular/core';
import {TableService} from '../../services/table.service';
import {AddedTime, ClientSession, PlaySession, Status, Table, Time} from '../../model/model';
import {DateUtils} from '../../services/DateUtils';

// TODO: when time finishes leave on the screen until manually deleted and change color

@Component({
  selector: 'app-table-manager-container',
  templateUrl: './table-manager-container.component.html',
  styleUrls: ['./table-manager-container.component.scss']
})
export class TableManagerContainerComponent implements OnInit {
  public tables: Table[];
  public sessions: Map<number, ClientSession> = new Map<number, ClientSession>();

  constructor(private tableService: TableService) {
  }

  ngOnInit(): void {
    this.tableService.getTables()
      .subscribe(tables => {
        this.tables = tables;
      });
  }

  public handleTableSessionCreated(playSession: PlaySession) {
    if (this.sessions.get(playSession.table.number)) {
      throw Error('Not session not expected');
    }

    const session = {
      startTime: playSession.startTime,
      endTime: playSession.endTime,
      duration: playSession.duration,
      clients: playSession.clients,
      elapsedTime: playSession.elapsedTime,
    } as ClientSession;


    session.totalMoney = playSession.rate * DateUtils.toHours(playSession.duration);
    session.spentMoney = 0;
    if (playSession.paid === true) {
      session.paidMoney = session.totalMoney;
    }
    session.needToPay = session.totalMoney - session.paidMoney;

    session.playSessions.push(playSession);
    playSession.clientSession = session;
  }

  public handleTimeAdded(addedTime: AddedTime) {
    const session = this.sessions.get(addedTime.table.number);
    const lastSession = session.playSessions[session.playSessions.length - 1];

    const updatedDuration : Time = DateUtils.addTimes(lastSession.duration, addedTime.duration);
    const updatedEndTime : Date = DateUtils.addTime(lastSession.endTime, addedTime.duration);
    const newSession = {
      duration: updatedDuration,
      startTime: lastSession.startTime,
      endTime: updatedEndTime,
      rate: addedTime.table.rate,
      table: lastSession.table,
      clients: lastSession.clients,
      elapsedTime: lastSession.elapsedTime,
      clientSession: session
    } as PlaySession;

    lastSession.status = Status.FINISHED;

    session.duration = DateUtils.addTimes(session.duration, addedTime.duration);
    session.endTime = updatedEndTime;

    const additionalPayment = addedTime.table.rate * DateUtils.toHours(addedTime.duration);
    session.totalMoney = session.totalMoney + additionalPayment;
    if (addedTime.paid === true) {
      session.paidMoney = session.paidMoney + additionalPayment;
    }
    session.needToPay = session.totalMoney - session.paidMoney;
    // session.spentMoney = ?

    session.playSessions.push(newSession);
  }
}
