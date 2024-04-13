import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../../../environments/environment";
import {TradesProductSaleDto} from "../../../model/trades-product.model";
 // Assume this exists

@Injectable({
  providedIn: 'root'
})
export class TradesProductSaleService {
  private apiUrl = `${environment.baseUrl}/gym-manager`;

  constructor(private http: HttpClient) { }

  getAllTradesProductSales(): Observable<TradesProductSaleDto[]> {
    return this.http.get<TradesProductSaleDto[]>(`${this.apiUrl}/trades/productSale`);
  }

  getTradesProductSale(id: string): Observable<TradesProductSaleDto> {
    return this.http.get<TradesProductSaleDto>(`${this.apiUrl}/trades/productSale/${id}`);
  }

  createTradesProductSale(dto: TradesProductSaleDto): Observable<TradesProductSaleDto> {
    return this.http.post<TradesProductSaleDto>(`${this.apiUrl}/trades/productSale`, dto);
  }

  updateTradesProductSale(id: string, dto: TradesProductSaleDto): Observable<TradesProductSaleDto> {
    return this.http.put<TradesProductSaleDto>(`${this.apiUrl}/trades/productSale/${id}`, dto);
  }
}
