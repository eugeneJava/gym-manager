import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {NgbPopover} from "@ng-bootstrap/ng-bootstrap";
import {Product, ProductItem} from "../../model/app.models";

@Component({
  selector: 'product-item-sale',
  templateUrl: './product-item-sale.component.html',
  styleUrls: ['./product-item-sale.component.css']
})
export class ProductItemSaleComponent implements OnInit {

  @Input() public productItem: ProductItem;
  @Output() public markPaidEvent: EventEmitter<void> = new EventEmitter<void>();
  @Output() public deleteProductEvent: EventEmitter<string> = new EventEmitter<string>();

  @ViewChild('popover') public popover: NgbPopover;
  constructor() {
  }

  ngOnInit(): void {
  }

  public openPopover(): void {
    this.popover.open();
  }

  public markPaid(): void {
    this.markPaidEvent.emit();
    this.popover.close();
  }

  public deleteProduct(productId: string): void {
    this.deleteProductEvent.emit(productId);
    this.popover.close();
  }

}
