import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {Table} from '../model/model';

@Injectable({
  providedIn: 'root'
})
export class TableService {

  constructor() { }

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
}
