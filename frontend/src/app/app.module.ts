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
import { TableSessionCloseDialogComponent } from './table-manager/components/table-session-close-dialog/table-session-close-dialog.component';

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
    TableSessionCloseDialogComponent
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
