import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {
  TableManagerContainerComponent
} from "./table-manager/containers/table-manager-container/table-manager-container.component";
import {ProductsContainerComponent} from "./trades/product/container/products-container.component";
import {
  ProcurementContainerComponent
} from "./trades/purchase/container/procurement-container/procurement-container.component";
import {TradesParcelContainerComponent} from "./trades/parcel/container/trades-parcel-container.component";
import {TradesProductSaleContainerComponent} from "./trades/sales/container/trades-product-sale-container.component";
import {TradesMainViewComponent} from "./trades/main/trades-main-view/trades-main-view.component";
import {
  PurchaseStatisticsListComponent
} from "./trades/purchase/components/purchase-statistics-list/purchase-statistics-list.component";
import {
  TradesProductSaleStatisticsComponent
} from "./trades/sales/components/sales-statistics/trades-product-sale-statistics.component";
import {
  SalesSimpleSellRacketFlowComponent
} from "./trades/sales/components/sales-simple-sell-racket-flow/sales-simple-sell-racket-flow.component";

const routes : Routes = [
  {
    path: 'tables',
    component: TableManagerContainerComponent
  },

  {
    path: 'trades',
    component: TradesMainViewComponent,
    children: [
      {
        path: 'products',
        component: ProductsContainerComponent
      },
      {
        path: 'purchase',
        component: ProcurementContainerComponent
      }
      ,
      {
        path: 'parcel',
        component: TradesParcelContainerComponent
      }
      ,
      {
        path: 'sales',
        component: TradesProductSaleContainerComponent
      },

      {
        path: 'purchaseStatistics',
        component: PurchaseStatisticsListComponent
      },

      {
        path: 'salesStatistics',
        component: TradesProductSaleStatisticsComponent
      },

      {
        path: 'tradeStatistics',
        component: SalesSimpleSellRacketFlowComponent,
      }
    ]
  },

  {
    path: 'simpleSaleRacketFlow',
    component: SalesSimpleSellRacketFlowComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
