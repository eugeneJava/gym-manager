import {Component, OnInit} from '@angular/core';
import {ProductItemService} from "../services/product-item.service";
import {ProductService} from "../services/product-service.service";
import {mergeMap, tap} from "rxjs/operators";
import {Product, ProductItem} from "../../model/app.models";

@Component({
  selector: 'product-manager',
  templateUrl: './product-manager.component.html',
  styleUrls: ['./product-manager.component.css']
})
export class ProductManagerComponent implements OnInit {
  public productItems = new Map<string, ProductItem[]>();
  public products: Product[] = [];

  constructor(private productItemService: ProductItemService,
              private productService: ProductService) {
  }

  public ngOnInit(): void {
    this.productService.getProducts()
      .pipe(
        tap((products: Product[]) => {
          this.products = products;
          this.initProductItems(products)
        }),
        mergeMap(() => this.productItemService.getProductsForToday())
      )
      .subscribe((productItems: ProductItem[]) => {
        productItems.forEach((item) => {
          const items: ProductItem[] = this.productItems.get(item.product.name);
          items.push(item);
        });

        this.sortItemsByTable();
      });
  }

  private initProductItems(products: Product[]): void {
    products.forEach((product: Product) => {
      this.productItems.set(product.name, []);
    });
  }

  private sortItemsByTable(): void {
    this.products.forEach((product: Product) => {
      const items: ProductItem[] = this.productItems.get(product.name);

      items.sort(this.productSortFn);
    });
  }

  public addProduct(productItem: ProductItem): void {
    this.productItemService.addProduct(productItem).subscribe((addedProduct) => {
      const items: ProductItem[] = this.productItems.get(productItem.product.name);
      items.push(addedProduct);

      items.sort(this.productSortFn);
    });
  }

  productSortFn = (p1, p2) => {
    if (p1.tableNumber && p2.tableNumber) {
      return p1.tableNumber - p2.tableNumber;
    }

    if (p1.tableNumber && !p2.tableNumber) {
      return -1;
    }

    if (p2.tableNumber && !p1.tableNumber) {
      return 1;
    }
    return 0;
  };

  public deleteProduct(productItem: ProductItem): void {
    this.productItemService.deleteProduct(productItem).subscribe(() => {
      const items: ProductItem[] = this.productItems.get(productItem.product.name);
      const index: number = items.findIndex((item: ProductItem) => item.id === productItem.id);
      items.splice(index, 1);
    });
  }

  public markPaid(productItem: ProductItem): void {
    this.productItemService.markPaid(productItem.id).subscribe(() => productItem.paid = true);
  }
}
