import { Component, Input, EventEmitter, Output } from '@angular/core';
import {TradesProductSaleDto} from "../../../../model/trades-product.model";

@Component({
  selector: 'app-trades-product-sale-list',
  templateUrl: './tradesProductSale-list.component.html'
})
export class TradesProductSaleListComponent {
  @Input() tradesProductSales: TradesProductSaleDto[] = [];
  @Output() editEvent = new EventEmitter<TradesProductSaleDto>();

  editSale(sale: TradesProductSaleDto): void {
    this.editEvent.emit(sale);
  }
}
