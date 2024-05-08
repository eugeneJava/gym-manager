import {Component, OnInit} from '@angular/core';
import {TradesProductSaleTotalStatisticsDto} from "../../../../model/trades-product.model";
import {TradesProductSaleService} from "../../services/tradesProductSale.service";

@Component({
  selector: 'trades-product-sale-statistics',
  templateUrl: './trades-product-sale-statistics.component.html',
  styleUrls: ['./trades-product-sale-statistics.component.scss']
})
export class TradesProductSaleStatisticsComponent  implements OnInit {
  statistics: TradesProductSaleTotalStatisticsDto;

  constructor(
    private tradesProductSaleService: TradesProductSaleService
  ) { }

  ngOnInit(): void {
    this.loadTradesProductSaleStatistics();
  }

  loadTradesProductSaleStatistics(): void {
    this.tradesProductSaleService.getAllTradesProductSaleStatistics().subscribe(
      (statistics: TradesProductSaleTotalStatisticsDto) => {
        this.statistics = statistics;
      },
      error => {
        console.error('There was an error while fetching TradesProductSales', error);
      }
    );
  }
}
