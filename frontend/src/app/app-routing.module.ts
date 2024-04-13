import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
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
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
