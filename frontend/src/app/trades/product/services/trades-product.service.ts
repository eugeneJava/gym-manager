import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {TradesProductCategoryDto, TradesProductDto} from "../../../model/trades-product.model";
import {environment} from "../../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class TradesProductService {
  private apiUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  getProducts(): Observable<TradesProductDto[]> {
    return this.http.get<TradesProductDto[]>(`${this.apiUrl}/gym-manager/trades/product`);
  }

   /*getProducts(): Observable<TradesProduct[]> {
    return of([
      {
        id: Math.random().toString(),
        code: Math.random().toString(),
        name: "Product A",
        comments: "First product comments."
      },
      {
        id: Math.random().toString(),
        code: Math.random().toString(),
        name: "Product B",
        comments: "Second product comments."
      },
      {
        id: Math.random().toString(),
        code: Math.random().toString(),
        name: "Product C",
        comments: "Third product comments."
      },
      {
        id: Math.random().toString(),
        code: Math.random().toString(),
        name: "Product D",
        comments: "Fourth product comments."
      }
    ])
  }*/

  createProduct(product: TradesProductDto): Observable<TradesProductDto> {
    return this.http.post<TradesProductDto>(`${this.apiUrl}/gym-manager/trades/product`, product);
  }

  updateProduct(product: TradesProductDto): Observable<TradesProductDto> {
    return this.http.put<TradesProductDto>(`${this.apiUrl}/gym-manager/trades/product/${product.id}`, product);
  }

  getCategories(): Observable<TradesProductCategoryDto[]> {
    return this.http.get<TradesProductCategoryDto[]>(`${this.apiUrl}/gym-manager/trades/category`);
  }
}
