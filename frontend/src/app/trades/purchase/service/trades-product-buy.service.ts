import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {ProductBuyStatisticsDto, TradesProductBuyDto} from "../../../model/trades-product.model";
import {environment} from "../../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class TradesProductBuyService {
  constructor(private http: HttpClient) {}

  getAllProductBuy(): Observable<TradesProductBuyDto[]> {
    return this.http.get<TradesProductBuyDto[]>(`${environment.baseUrl}/gym-manager/trades/productBuy`);
  }

  getProductBuy(id: string): Observable<TradesProductBuyDto> {
    return this.http.get<TradesProductBuyDto>(`${environment.baseUrl}/gym-manager/trades/productBuy/${id}`);
  }

  createProductBuy(dto: TradesProductBuyDto): Observable<TradesProductBuyDto> {
    return this.http.post<TradesProductBuyDto>(`${environment.baseUrl}/gym-manager/trades/productBuy`, dto);
  }

  updateProductBuy(id: string, dto: TradesProductBuyDto): Observable<TradesProductBuyDto> {
    return this.http.put<TradesProductBuyDto>(`${environment.baseUrl}/gym-manager/trades/productBuy/${id}`, dto);
  }

  getProductBuyStatistics(): Observable<ProductBuyStatisticsDto[]> {
    return this.http.get<ProductBuyStatisticsDto[]>(`${environment.baseUrl}/gym-manager/trades/productBuy/statistics`);
  }
}
