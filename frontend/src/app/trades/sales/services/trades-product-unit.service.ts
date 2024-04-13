import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../../../environments/environment";
import {ProductsAvailableForSaleDto, TradesProductUnitDto} from "../../../model/trades-product.model";
 // Assume this exists

@Injectable({
  providedIn: 'root'
})
export class TradesTradesProductUnitService {
  private apiUrl = `${environment.baseUrl}/gym-manager`;

  constructor(private http: HttpClient) { }

  getNotSoldTradesProductUnits(): Observable<TradesProductUnitDto[]> {
    return this.http.get<TradesProductUnitDto[]>(`${this.apiUrl}/trades/productUnit/available`);
  }

  getAvailebleForSaleProducts(): Observable<ProductsAvailableForSaleDto[]> {
    return this.http.get<ProductsAvailableForSaleDto[]>(`${this.apiUrl}/trades/productUnit/availableForSale`);
  }


  getTradesTradesProductUnit(id: string): Observable<TradesProductUnitDto> {
    return this.http.get<TradesProductUnitDto>(`${this.apiUrl}/trades/productUnit/${id}`);
  }

  createTradesTradesProductUnit(dto: TradesProductUnitDto): Observable<TradesProductUnitDto> {
    return this.http.post<TradesProductUnitDto>(`${this.apiUrl}/trades/productUnit`, dto);
  }

  updateTradesTradesProductUnit(id: string, dto: TradesProductUnitDto): Observable<TradesProductUnitDto> {
    return this.http.put<TradesProductUnitDto>(`${this.apiUrl}/trades/productUnit/${id}`, dto);
  }
}
