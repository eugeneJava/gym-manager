import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {Product} from "../../model/app.models";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class ProductService {

  constructor(private http: HttpClient) { }

  public getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>('products');
  }
}
