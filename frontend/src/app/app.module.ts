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
import {ProductSalesDailyListComponent} from './product-manager/components/product-sales-daily-list/product-sales-daily-list.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ToastrModule} from "ngx-toastr";
import {ClientSearchComponent} from './client-manager/components/client-search/client-search.component';
import {ClientManagerContentComponent} from './client-manager/containers/client-manager-content/client-manager-content.component';
import {AddProductPopoverComponent} from './product-manager/components/add-product-popover/add-product-popover.component';
import {AddProductItemComponent} from './product-manager/components/add-product-item/add-product-item.component';
import {ProductItemSaleComponent} from './product-manager/components/product-item-sale/product-item-sale.component';
import {ProductItemService} from "./product-manager/services/product-item.service";
import {StatusSectionComponent} from './status-manager/status-section.component';
import {DateFormatPipe} from './status-manager/filters/date-format.pipe';
import {ProductService} from "./product-manager/services/product-service.service";
import {far} from "@fortawesome/free-regular-svg-icons";

@NgModule({
  declarations: [
    AppComponent,
    ClientAddComponent,
    MainWidgetComponent,
    ProductManagerComponent,
    ProductSalesDailyListComponent,
    ClientSearchComponent,
    ClientManagerContentComponent,
    AddProductPopoverComponent,
    AddProductItemComponent,
    ProductItemSaleComponent,
    StatusSectionComponent,
    DateFormatPipe
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
