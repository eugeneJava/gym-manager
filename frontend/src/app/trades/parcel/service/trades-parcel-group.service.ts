import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../../../environments/environment";
import { TradesParcelGroupDto } from 'src/app/model/trades-product.model';

@Injectable({
  providedIn: 'root'
})
export class TradesParcelGroupService {
  private apiUrl = `${environment.baseUrl}/gym-manager/trades/parcelGroup`;

  constructor(private http: HttpClient) { }

  getAll(): Observable<TradesParcelGroupDto[]> {
    return this.http.get<TradesParcelGroupDto[]>(`${this.apiUrl}`);
  }

  get(id: string): Observable<TradesParcelGroupDto> {
    return this.http.get<TradesParcelGroupDto>(`${this.apiUrl}/${id}`);
  }

  create(parcelGroup: TradesParcelGroupDto): Observable<TradesParcelGroupDto> {
    return this.http.post<TradesParcelGroupDto>(this.apiUrl, parcelGroup);
  }

  update(parcelGroup: TradesParcelGroupDto): Observable<TradesParcelGroupDto> {
    return this.http.put<TradesParcelGroupDto>(`${this.apiUrl}/${parcelGroup.id}`, parcelGroup);
  }

  getParcelGroupsWithoutParcel(): Observable<TradesParcelGroupDto[]> {
    return this.http.get<TradesParcelGroupDto[]>(`${this.apiUrl}/withoutParcel`);
  }
}
