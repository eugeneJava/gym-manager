import { Component, EventEmitter, Input, Output } from '@angular/core';
import {TradesProductDto} from "../../../../model/trades-product.model";

@Component({
  selector: 'app-products-list',
  templateUrl: './products-list.component.html',
  styleUrls: ['./products-list.component.scss']
})
export class ProductsListComponent {
  @Input() products: TradesProductDto[];
  @Output() edit: EventEmitter<TradesProductDto> = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
  }

  onEdit(product: TradesProductDto) {
    this.edit.emit(product);
  }
}
