import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {ClientAddComponent} from './client-manager/components/client-add/client-add.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ClientService} from './client-manager/components/services/client.service';
import {HttpClientModule} from '@angular/common/http';
import {NameService} from './client-manager/components/services/name.service';
import {MainWidgetComponent} from './main-widget/main-widget.component';
import {ProductManagerComponent} from './product-manager/containers/product-manager.component';
import {FaIconLibrary, FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {fas} from '@fortawesome/free-solid-svg-icons';
import {
  ProductSalesDailyListComponent
} from './product-manager/components/product-sales-daily-list/product-sales-daily-list.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ToastrModule} from "ngx-toastr";
import {ClientSearchComponent} from './client-manager/components/client-search/client-search.component';
import {
  ClientManagerContentComponent
} from './client-manager/containers/client-manager-content/client-manager-content.component';
import {
  AddProductPopoverComponent
} from './product-manager/components/add-product-popover/add-product-popover.component';
import {AddProductItemComponent} from './product-manager/components/add-product-item/add-product-item.component';
import {ProductItemSaleComponent} from './product-manager/components/product-item-sale/product-item-sale.component';
import {ProductItemService} from "./product-manager/services/product-item.service";
import {StatusSectionComponent} from './status-manager/status-section.component';
import {DateFormatPipe} from './status-manager/filters/date-format.pipe';
import {ProductService} from "./product-manager/services/product-service.service";
import {far} from "@fortawesome/free-regular-svg-icons";
import {TablePickerComponent} from "./table-manager/components/table-picker/table-picker.component";
import {MovePlayerDialogComponent} from "./table-manager/components/move-player-dialog/move-player-dialog.component";
import {MoneyFormatPipe} from "./table-manager/pipes/money-format.pipe";
import {TimeFormatPipe} from "./table-manager/pipes/time-format.pipe";
import {DurationPickerComponent} from "./table-manager/components/duration-picker/duration-picker.component";
import {AddTimeDialogComponent} from "./table-manager/components/add-time-dialog/add-time-dialog.component";
import {PlaySessionEditComponent} from "./table-manager/components/play-session-edit/play-session-edit.component";
import {TableSchedulerComponent} from "./table-manager/components/table-scheduler/table-scheduler.component";
import {
  TableManagerContainerComponent
} from "./table-manager/containers/table-manager-container/table-manager-container.component";
import {
  TableSessionCloseDialogComponent
} from './table-manager/components/table-session-close-dialog/table-session-close-dialog.component';
import {ProductsContainerComponent} from './trades/product/container/products-container.component';
import {ProductsListComponent} from './trades/product/components/products-list/products-list.component';
import {ProductsEditComponent} from './trades/product/components/products-edit/products-edit.component';
import {
  PurchaseWithParcelEdit
} from './trades/purchase/components/purchase-with-parcel-edit/purchase-with-parcel-edit.component';
import {ProcurementListComponent} from './trades/purchase/components/procurement-list/procurement-list.component';
import {
  ProcurementContainerComponent
} from './trades/purchase/container/procurement-container/procurement-container.component';
import {TradesParcelEditComponent} from "./trades/parcel/components/parcel-edit/trades-parcel-edit.component";
import {TradesParcelListComponent} from "./trades/parcel/components/parcel-list/trades-parcel-list.component";
import {TradesParcelContainerComponent} from "./trades/parcel/container/trades-parcel-container.component";
import {TradesProductSaleContainerComponent} from "./trades/sales/container/trades-product-sale-container.component";
import {TradesProductSaleEditComponent} from "./trades/sales/components/sales-edit/tradesProductSale-edit.component";
import {TradesProductSaleListComponent} from "./trades/sales/components/sales-list/tradesProductSale-list.component";
import {TradesMainViewComponent} from './trades/main/trades-main-view/trades-main-view.component';
import {
  PurchaseStatisticsListComponent
} from './trades/purchase/components/purchase-statistics-list/purchase-statistics-list.component';
import {PurchaseEdit} from "./trades/purchase/components/purchase-edit/purchase-edit.component";
import {
  TradesProductSaleGroupEditComponent
} from "./trades/sales/components/sales-group-edit/tradesProductSale-group-edit.component";
import {SaleGroupComponent} from "./trades/sales/components/sales-group/sale-group.component";
import {
  TradesProductSaleStatisticsComponent
} from "./trades/sales/components/sales-statistics/trades-product-sale-statistics.component";
import {ProductTradeStatisticsComponent} from "./trades/statistics/components/product-trade-statistics.component";
import { SalesSimpleSellRacketFlowComponent } from './trades/sales/components/sales-simple-sell-racket-flow/sales-simple-sell-racket-flow.component';
import {
  SalesSimpleSellProductFlowComponent
} from "./trades/sales/components/sales-simple-sell-product-flow/sales-simple-sell-product-flow.component";

@NgModule({
  declarations: [
    AppComponent,
    ClientAddComponent,
    MainWidgetComponent,
    ProductManagerComponent,
    ProductSalesDailyListComponent,
    ClientManagerContentComponent,
    AddProductPopoverComponent,
    AddProductItemComponent,
    ProductItemSaleComponent,
    StatusSectionComponent,
    DateFormatPipe,

    TableManagerContainerComponent,
    TableSchedulerComponent,
    PlaySessionEditComponent,
    ClientSearchComponent,
    AddTimeDialogComponent,
    DurationPickerComponent,
    TimeFormatPipe,
    MoneyFormatPipe,
    MovePlayerDialogComponent,
    TablePickerComponent,
    TableSessionCloseDialogComponent,
    ProductsContainerComponent,
    ProductsListComponent,
    ProductsEditComponent,
    PurchaseWithParcelEdit,
    PurchaseEdit,
    ProcurementListComponent,
    ProcurementContainerComponent,
    TradesParcelEditComponent,
    TradesParcelListComponent,
    TradesParcelContainerComponent,
    TradesProductSaleGroupEditComponent,

    TradesProductSaleContainerComponent,
    TradesProductSaleEditComponent,
    TradesProductSaleListComponent,
    TradesMainViewComponent,
    PurchaseStatisticsListComponent,
    SaleGroupComponent,
    TradesProductSaleStatisticsComponent,
    ProductTradeStatisticsComponent,
    SalesSimpleSellRacketFlowComponent,
    SalesSimpleSellProductFlowComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    FontAwesomeModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot()
  ],
  providers: [
    ClientService,
    NameService,
    ProductItemService,
    ProductService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(library: FaIconLibrary) {
    library.addIconPacks(fas,far);
  }
}
