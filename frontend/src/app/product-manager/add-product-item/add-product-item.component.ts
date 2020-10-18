import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {Product, ProductItem} from "../../model/app.models";
import {NgbPopover} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'add-product-item',
  templateUrl: './add-product-item.component.html',
  styleUrls: ['./add-product-item.component.css']
})
export class AddProductItemComponent implements OnInit {

  @Input() public product: Product;
  @Output() public addProductEvent: EventEmitter<ProductItem> = new EventEmitter<ProductItem>();

  @ViewChild('popover') public popover: NgbPopover;

  constructor() { }

  ngOnInit(): void {
  }

  public openPopover(): void {
    this.popover.open();
  }

  public addProduct(productItem: ProductItem): void {
    this.popover.close();
    this.addProductEvent.emit(productItem);
  }
}
