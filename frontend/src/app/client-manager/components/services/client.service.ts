import {Injectable} from '@angular/core';
import {Client} from '../../../model/app.models';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable()
export class ClientService {

  constructor(private http: HttpClient) { }

  public search(term: string): Observable<Client[]> {
    const params = {
      term: term
    };
    return this.http.get<Client[]>('clients', {params:params});
  }

  public addClient(client: Client): Observable<Client> {
    return this.http.post<Client>('clients', client);
  }

  public getClient(phone: string): Observable<Client> {
    return this.http.get<Client>('clients/' + phone);
  }
}
