import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../../../environments/environment";
import { ProductTradeStatisticsDto } from "../../../model/trades-product.model";

@Injectable({
  providedIn: 'root'
})
export class ProductTradeStatisticsService {
  private apiUrl = `${environment.baseUrl}/gym-manager`;

  constructor(private http: HttpClient) { }

  getProductTradeStatistics(): Observable<ProductTradeStatisticsDto> {
    return this.http.get<ProductTradeStatisticsDto>(`${this.apiUrl}/trades/history`);
  }
}
