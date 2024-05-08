import {Component, OnInit} from '@angular/core';
import {ProductTradeStatisticsDto} from "../../../model/trades-product.model";
import {ProductTradeStatisticsService} from "../service/product-trades-statiscs.service";

@Component({
  selector: 'product-trade-statistics',
  templateUrl: './product-trade-statistics.component.html'
})
export class ProductTradeStatisticsComponent implements OnInit {
  statistics: ProductTradeStatisticsDto;

  constructor(

    private productTradeStatisticsService: ProductTradeStatisticsService
  ) { }

  ngOnInit(): void {
    this.loadProductTradeStatistics();
  }

  loadProductTradeStatistics(): void {
    this.productTradeStatisticsService.getProductTradeStatistics().subscribe(
      (statistics: ProductTradeStatisticsDto) => {
        this.statistics = statistics;
      }
    );
  }
}
