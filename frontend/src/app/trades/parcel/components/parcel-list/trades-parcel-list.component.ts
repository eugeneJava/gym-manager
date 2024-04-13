// src/app/components/trades-parcel-list/trades-parcel-list.component.ts

import {Component, EventEmitter, Input, Output} from '@angular/core';
import {TradesParcelDto} from "../../../../model/trades-product.model";

@Component({
  selector: 'app-trades-parcel-list',
  templateUrl: './trades-parcel-list.component.html'
})
export class TradesParcelListComponent {
  @Input() parcels: TradesParcelDto[] = [];
  @Output() edit = new EventEmitter<TradesParcelDto>();

  onEdit(parcel: TradesParcelDto): void {
    this.edit.emit(parcel);
  }
}
