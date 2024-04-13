import { Component, EventEmitter, Input, Output } from '@angular/core';
import {TradesProductBuyDto} from "../../../../model/trades-product.model";

@Component({
  selector: 'app-list',
  templateUrl: './procurement-list.component.html',
  styleUrls: ['./procurement-list.component.scss']
})
export class ProcurementListComponent {
  @Input() products: TradesProductBuyDto[] = [];
  @Output() edit: EventEmitter<TradesProductBuyDto> = new EventEmitter();

  constructor() { }

  onEdit(product: TradesProductBuyDto): void {
    this.edit.emit(product);
  }
}
