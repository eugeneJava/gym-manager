import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {TradesProductDto} from '../../../../model/trades-product.model'; // Adjust import path as necessary

@Component({
  selector: 'app-products-edit',
  templateUrl: './products-edit.component.html',
  styleUrls: ['./products-edit.component.scss']
})
export class ProductsEditComponent implements OnInit {
  @Input() product: TradesProductDto;
  productForm: FormGroup;

  constructor(public activeModal: NgbActiveModal, private fb: FormBuilder) {}

  ngOnInit() {
    this.productForm = this.fb.group({
      id: [this.product?.id],
      name: [this.product?.name, Validators.required],
      comments: [this.product?.comments]
    });
  }

  save() {
    if (this.productForm.valid) {
      this.activeModal.close(this.productForm.value);
    }
  }
}
