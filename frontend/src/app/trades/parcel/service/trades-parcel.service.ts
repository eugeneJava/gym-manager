// src/app/services/trades-parcel.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {TradesParcelDto} from "../../../model/trades-product.model";
import {environment} from "../../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class TradesParcelService {
  private apiUrl = `${environment.baseUrl}/gym-manager/trades/parcel`;

  constructor(private http: HttpClient) { }

  getAll(): Observable<TradesParcelDto[]> {
    return this.http.get<TradesParcelDto[]>(this.apiUrl);
  }

  get(id: string): Observable<TradesParcelDto> {
    return this.http.get<TradesParcelDto>(`${this.apiUrl}/${id}`);
  }

  create(parcel: TradesParcelDto): Observable<TradesParcelDto> {
    return this.http.post<TradesParcelDto>(this.apiUrl, parcel);
  }

  update(parcel: TradesParcelDto): Observable<TradesParcelDto> {
    return this.http.put<TradesParcelDto>(`${this.apiUrl}/${parcel.id}`, parcel);
  }
}
