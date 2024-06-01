import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormControl, UntypedFormGroup, Validators} from '@angular/forms';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {TradesProductBuyDto, TradesProductDto} from "../../../../model/trades-product.model";
import {DateUtils} from "../../../../model/date-utils";

@Component({
  selector: 'app-edit',
  templateUrl: './purchase-edit.component.html',
  styleUrls: ['./purchase-edit.component.scss']
})
export class PurchaseEditComponent implements OnInit {
  @Input() productBuy: TradesProductBuyDto;
  productForm: UntypedFormGroup;
  products: TradesProductDto[] = [];

  constructor(
    public activeModal: NgbActiveModal,
    private fb: FormBuilder
  ) {

  }

  ngOnInit(): void {
    this.productForm = this.fb.group({
      id: new FormControl<string>(null),
      trackId: new FormControl<string>(null,  Validators.required),
      purchaseDate: new FormControl<string>(DateUtils.nowAsDate(), Validators.required),
    });


    this.productForm.patchValue(this.productBuy);
  }

  saveProductBuy(): void {
      const formValue = this.productForm.getRawValue(); // Use getRawValue() if you need to include disabled controls, else use value.
      this.activeModal.close(formValue);
  }

  get id(): FormControl {
    return this.productForm.get('id') as FormControl;
  }

  get trackId(): FormControl {
    return this.productForm.get('trackId') as FormControl;
  }

  get comments(): FormControl {
    return this.productForm.get('comments') as FormControl;
  }

  get purchaseDate(): FormControl {
    return this.productForm.get('purchaseDate') as FormControl;
  }
}
