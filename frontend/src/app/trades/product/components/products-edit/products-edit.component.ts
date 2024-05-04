import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {TradesProductCategoryDto, TradesProductDto} from '../../../../model/trades-product.model';
import {TradesProductService} from "../../services/trades-product.service"; // Adjust import path as necessary

@Component({
  selector: 'app-products-edit',
  templateUrl: './products-edit.component.html',
  styleUrls: ['./products-edit.component.scss']
})
export class ProductsEditComponent implements OnInit {
  @Input() product: TradesProductDto;
  productForm: FormGroup;
  productCategories: TradesProductCategoryDto[] = []; // Add this line

  constructor(public activeModal: NgbActiveModal,
              private fb: FormBuilder,
              private tradesProductService: TradesProductService) {} // Inject TradesProductService

  ngOnInit() {
    this.productForm = this.fb.group({
      id: [this.product?.id],
      name: [this.product?.name, Validators.required],
      comments: [this.product?.comments],
      recommendedPrice: [this.product?.recommendedPrice],
      category: [this.product?.category, Validators.required],
    });

    this.loadProductCategories(); // Add this line
  }

  loadProductCategories() { // Add this method
    this.tradesProductService.getCategories().subscribe(categories => {
      this.productCategories = categories;
    });
  }

  save() {
    if (this.productForm.valid) {
      this.activeModal.close(this.productForm.value);
    }
  }

  categoryId(t1: TradesProductCategoryDto, t2: TradesProductCategoryDto): boolean {
    return t1 && t2 ? t1.id === t2.id : t1 === t2;
  }
}
