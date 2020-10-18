import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Product, ProductItem} from '../../model/app.models';

@Component({
  selector: 'app-product-sales-daily-list',
  templateUrl: './product-sales-daily-list.component.html',
  styleUrls: ['./product-sales-daily-list.component.css']
})
export class ProductSalesDailyListComponent implements OnInit {
  @Input() public productItems: ProductItem[] = [];
  @Input() public product: Product;

  @Output() public deleteProductEvent: EventEmitter<ProductItem> = new EventEmitter<ProductItem>();
  @Output() public markPaidEvent: EventEmitter<ProductItem> = new EventEmitter<ProductItem>();

  public faIcon: string;

  constructor() { }

  ngOnInit(): void {
  }

  public markPaid(productItem: ProductItem): void {
    this.markPaidEvent.emit(productItem);
    productItem.paid = true;
  }

  public deleteProduct(productId: string) {
    let productItem: ProductItem = this.productItems.find((item: ProductItem) => item.id === productId);
    this.deleteProductEvent.emit(productItem);
  }

}
