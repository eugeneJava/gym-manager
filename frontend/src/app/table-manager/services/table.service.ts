import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {ClientSession, Table, TableSession} from '../model/model';
import {HttpClient} from "@angular/common/http";
import {Client} from "../../model/app.models";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class TableService {

  constructor(private http: HttpClient) { }

  public getTables(): Observable<Table[]> {
    const table1 : Table = {
      number: 1,
      rate: 100
    };

    const table2 : Table = {
      number: 2,
      rate: 80
    };

    const table3 : Table = {
      number: 3,
      rate: 80
    };

    const table4 : Table = {
      number: 4,
      rate: 80
    };

    const table5 : Table = {
      number: 5,
      rate: 80
    };

    const table6 : Table = {
      number: 6,
      rate: 100
    };
    return of([table1, table2, table3, table4, table5, table6]);
  }

  public startSession(tableSession: TableSession): Observable<TableSession> {
    return this.http.post<TableSession>(`${environment.baseUrl}/gym-manager/tableSessions/start`, tableSession);
  }

  public closeSession(tableSession: TableSession): Observable<TableSession> {
    return this.http.post<TableSession>(`${environment.baseUrl}/gym-manager/tableSessions/${tableSession.id}/close`, {});
  }

  public getCurrentSessions(clientSessionId: string):  Observable<TableSession[]> {
    return this.http.get<TableSession[]>(`${environment.baseUrl}/gym-manager/clientSessions/${clientSessionId}/tableSessions`);
  }

  public getPreCloseCalculations(clientSessionId: string):  Observable<TableSession[]> {
    return this.http.get<TableSession[]>(`${environment.baseUrl}/gym-manager/clientSessions/${clientSessionId}/preCloseCalculations`);
  }

}
