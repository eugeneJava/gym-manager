import {Component, OnInit} from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {TradesProductService} from "../services/trades-product.service";
import {TradesProductDto} from "../../../model/trades-product.model";
import {ProductsEditComponent} from "../components/products-edit/products-edit.component";

@Component({
  selector: 'app-products-container',
  templateUrl: './products-container.component.html',
  styleUrls: ['./products-container.component.scss']
})
export class ProductsContainerComponent implements OnInit {
  products: TradesProductDto[] = [];

  constructor(private productService: TradesProductService, private modalService: NgbModal) { }

  ngOnInit() {
    this.loadProducts();
  }

  loadProducts() {
    this.productService.getProducts().subscribe(data => {
      this.products = data;
    });
  }

  addProduct() {
    const modalRef = this.modalService.open(ProductsEditComponent);
    modalRef.result.then((result) => {
      if (result) {
        this.productService.createProduct(result).subscribe(() => {
          this.loadProducts();
        });
      }
    });
  }

  editProduct(product: TradesProductDto) {
    const modalRef = this.modalService.open(ProductsEditComponent);
    modalRef.componentInstance.product = product;
    modalRef.result.then((result) => {
      if (result) {
        this.productService.updateProduct(result).subscribe(() => {
          this.loadProducts();
        });
      }
    });
  }
}
