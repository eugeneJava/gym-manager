import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {TradesProductBuyDto} from "../../../../model/trades-product.model";
import {TradesProductBuyService} from "../../service/trades-product-buy.service";
import {PurchaseWithParcelEdit} from "../../components/purchase-with-parcel-edit/purchase-with-parcel-edit.component";
import {PurchaseCreateComponent} from "../../components/purchase-create/purchase-edit.component";

@Component({
  selector: 'app-container',
  templateUrl: './procurement-container.component.html',
  styleUrls: ['./procurement-container.component.scss']
})
export class ProcurementContainerComponent implements OnInit {
  products: TradesProductBuyDto[] = [];

  constructor(
    private productService: TradesProductBuyService,
    private modalService: NgbModal
  ) {}

  ngOnInit() {
    this.loadProducts();
  }

  loadProducts() {
    this.productService.getAllProductBuy().subscribe(
      (data) => {
        this.products = data;
      },
      (error) => {
        console.error('There was an error retrieving products', error);
      }
    );
  }

  addPurchaseWithParcel() {
    const modalRef = this.modalService.open(PurchaseWithParcelEdit);
    modalRef.result.then((result) => {
      if (result) {
        this.productService.createProductBuyWithParcel(result).subscribe(
          (newProduct) => {
            this.products = [...this.products, newProduct];
            this.loadProducts(); // Refresh list to ensure consistency
          },
          (error) => {
            console.error('There was an error saving the new product', error);
          }
        );
      }
    });
  }

  addPurchase() {
    const modalRef = this.modalService.open(PurchaseCreateComponent);
    modalRef.result.then((result) => {
      if (result) {
        this.productService.createProductBuy(result).subscribe(
          (newProduct) => {
            this.products = [...this.products, newProduct];
            this.loadProducts(); // Refresh list to ensure consistency
          },
          (error) => {
            console.error('There was an error saving the new product', error);
          }
        );
      }
    });
  }

  editPurchase(productBuy: TradesProductBuyDto) {
    const modalRef = this.modalService.open(PurchaseWithParcelEdit);
    modalRef.componentInstance.productBuy = productBuy;
    modalRef.result.then((updatedProduct) => {
      if (updatedProduct) {
        this.productService.updateProductBuy(productBuy.id, updatedProduct).subscribe(
          () => {
            this.loadProducts(); // Refresh list to reflect the update
          },
          (error) => {
            console.error('There was an error updating the product', error);
          }
        );
      }
    });
  }
}

