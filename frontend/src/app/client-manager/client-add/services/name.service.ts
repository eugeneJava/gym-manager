import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Client} from "../../../model/app.models";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class NameService {

  constructor(private http: HttpClient) {
  }

  public search(term: string): Observable<string[]> {
    const params = {
      term: term
    };
    return this.http.get<string[]>('names', {params:params});
  }
}
