import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {Client} from '../model/model';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  constructor() { }

  public search(term: string): Observable<Client[]> {
    const params = {
      term
    };

    const client1 : Client = {
      name: 'Semen Paliy',
      phone: '+30885734844',
      nick: 'Satoshi'
    }

    const client2 : Client = {
      name: 'Semen Mukliy',
      phone: '+30885735644',
      nick: 'Emo'
    }

    const client3 : Client = {
      name: 'Pivon Erefeev',
      phone: '+30885735688',
      nick: 'Killa'
    }
    return of([client1, client2, client3]);
  }
}
