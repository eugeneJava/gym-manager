import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ProductItem} from "../../model/app.models";

@Injectable()
export class ProductItemService {

  constructor(private http: HttpClient) { }

  public getProductsForToday(): Observable<ProductItem[]> {
    return this.http.get<ProductItem[]>('productItems/forToday');
  }

  public addProduct(product: ProductItem): Observable<ProductItem> {
    return this.http.post<ProductItem>('productItems', product);
  }

  public markPaid(productItemId: string): Observable<ProductItem> {
    return this.http.put<ProductItem>('productItems/' + productItemId + '/markPaid', {});
  }

  public deleteProduct(product: ProductItem): Observable<ProductItem> {
    return this.http.delete<ProductItem>('productItems/' + product.id);
  }

 /* public addProduct(product: ProductItem): Observable<ProductItem> {
    return of({
      type: product.type,
      paid: product.paid,
      tableNumber: product.tableNumber,
      price: product.price,
      id: '23444'
    })
  }

  public markPaid(productItemId: string): Observable<ProductItem> {
    return this.http.put<ProductItem>('products/' + productItemId + '/markPaid', {});
  }

  public deleteProduct(product: ProductItem): Observable<ProductItem> {
    return this.http.delete<ProductItem>('products/' + product.id);
  }*/
}
