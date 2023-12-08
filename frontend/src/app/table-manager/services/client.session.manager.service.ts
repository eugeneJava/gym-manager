import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {ClientSession, Table, TableSession} from '../model/model';
import {HttpClient} from "@angular/common/http";
import {Client} from "../../model/app.models";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ClientSessionManager {

  constructor(private http: HttpClient) { }

  private clientSessions:Map<string, TableSession[]> = new Map<string, TableSession[]>();

  public addTableSession(tableSession: TableSession): void {
    if (!this.clientSessions.has(tableSession.clientSessionId)) {
       this.clientSessions.set(tableSession.clientSessionId, []);
    }

    this.clientSessions.get(tableSession.clientSessionId).push(tableSession);
  }

  public removeTableSession(tableSession: TableSession): void {

  }
}
