import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {TradesProductSaleDto} from "../../../model/trades-product.model";
import {TradesProductSaleService} from "../services/tradesProductSale.service";
import {TradesProductSaleEditComponent} from "../components/sales-edit/tradesProductSale-edit.component";

@Component({
  selector: 'app-trades-product-sale-container',
  templateUrl: './trades-product-sale-container.component.html'
})
export class TradesProductSaleContainerComponent implements OnInit {
  tradesProductSales: TradesProductSaleDto[] = [];

  constructor(
    private tradesProductSaleService: TradesProductSaleService,
    private modalService: NgbModal
  ) { }

  ngOnInit(): void {
    this.loadTradesProductSales();
  }

  loadTradesProductSales(): void {
    this.tradesProductSaleService.getAllTradesProductSales().subscribe(
      (data: TradesProductSaleDto[]) => {
        this.tradesProductSales = data;
      },
      error => {
        console.error('There was an error while fetching TradesProductSales', error);
      }
    );
  }

  addTradesProductSale(): void {
    const modalRef = this.modalService.open(TradesProductSaleEditComponent);
    modalRef.result.then((result) => {
      if (result) {
        this.tradesProductSaleService.createTradesProductSale(result).subscribe(
          (newSale: TradesProductSaleDto) => {
            this.tradesProductSales.push(newSale);
            this.tradesProductSales = [...this.tradesProductSales]; // Refresh the list in the view
          },
          error => {
            console.error('There was an error while creating a TradesProductSale', error);
          }
        );
      }
    });
  }

  editTradesProductSale(sale: TradesProductSaleDto): void {
    const modalRef = this.modalService.open(TradesProductSaleEditComponent);
    modalRef.componentInstance.sale = sale;
    modalRef.result.then((result) => {
      if (result) {
        this.tradesProductSaleService.updateTradesProductSale(result.id, result).subscribe(
          () => {
            this.loadTradesProductSales(); // Refresh the list to show the updated data
          },
          error => {
            console.error('There was an error while updating the TradesProductSale', error);
          }
        );
      }
    });
  }
}
