import {Component, OnInit} from '@angular/core';
import {TableService} from '../../services/table.service';
import {AddedTime, ClientSession, TableSession, Status, Table, Time} from '../../model/model';
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

  public handleTableSessionCreated(tableSession: TableSession) {
    if (this.sessions.get(tableSession.tableNumber)) {
      throw Error('Not session not expected');
    }

    const session = {
      startTime: tableSession.startDate,
      endTime: tableSession.endDate,
      duration: tableSession.duration,
      clients: tableSession.clients,
      elapsedTime: tableSession.elapsedTime,
    } as ClientSession;


    session.totalMoney = tableSession.rate * DateUtils.toHours(tableSession.duration);
    session.spentMoney = 0;
    if (tableSession.paid === true) {
      session.paidMoney = session.totalMoney;
    }
    session.needToPay = session.totalMoney - session.paidMoney;

    session.tableSessions.push(tableSession);
  }

  public handleTimeAdded(addedTime: AddedTime) {
    const session = this.sessions.get(addedTime.table.number);
    const lastSession = session.tableSessions[session.tableSessions.length - 1];

    const updatedDuration : Time = DateUtils.addTimes(lastSession.duration, addedTime.duration);
    const updatedEndTime  = DateUtils.addTime(lastSession.endDate, addedTime.duration);
    const newSession = {
      duration: updatedDuration,
      startDate: lastSession.startDate,
      endDate: updatedEndTime,
      rate: addedTime.table.rate,
      tableNumber: lastSession.tableNumber,
      clients: lastSession.clients,
      elapsedTime: lastSession.elapsedTime
     // clientSessionId: session.i
    } as TableSession;

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

    session.tableSessions.push(newSession);
  }
}
