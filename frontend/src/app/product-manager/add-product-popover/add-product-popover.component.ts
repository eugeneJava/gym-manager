import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Product, ProductItem} from "../../model/app.models";

@Component({
  selector: 'add-product-popover',
  templateUrl: './add-product-popover.component.html',
  styleUrls: ['./add-product-popover.component.css']
})
export class AddProductPopoverComponent implements OnInit {
  @Input() public product: Product;
  @Output() public addProductEvent: EventEmitter<ProductItem> = new EventEmitter<ProductItem>();
  public productItem: ProductItem;

  constructor() { }

  ngOnInit(): void {
    this.productItem = {
      tableNumber: 1,
      paid: false,
      product: this.product
    } as ProductItem
  }

  public addProduct(): void {
    this.addProductEvent.emit(this.productItem);
  }
}
