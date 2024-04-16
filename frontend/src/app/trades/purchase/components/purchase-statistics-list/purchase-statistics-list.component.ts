import {Component, OnInit} from '@angular/core';
import {TradesProductBuyService} from "../../service/trades-product-buy.service";
import {ProductBuyStatisticsDto} from "../../../../model/trades-product.model";

@Component({
  selector: 'app-purchase-statistics-list',
  templateUrl: './purchase-statistics-list.component.html',
  styleUrls: ['./purchase-statistics-list.component.scss']
})
export class PurchaseStatisticsListComponent implements OnInit {

  productsBuyStatistics: ProductBuyStatisticsDto[] = [];

  constructor(
    private productService: TradesProductBuyService
  ) {
  }

  ngOnInit() {
    this.loadProductsBuyStatistics();
  }

  public loadProductsBuyStatistics() {
    this.productService.getProductBuyStatistics().subscribe(
      data => {
        this.productsBuyStatistics = data;
      }
    );
  }
}
