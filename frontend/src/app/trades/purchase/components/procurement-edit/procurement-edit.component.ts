import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {TradesParcelGroupDto, TradesProductBuyDto, TradesProductDto} from "../../../../model/trades-product.model";
import {TradesProductService} from "../../../product/services/trades-product.service";
import {DateUtils} from "../../../../model/date-utils";

interface ProductFormValue {
  id: FormControl<string>;
  product: FormControl<TradesProductDto>;
  parcelGroup: FormControl<TradesParcelGroupDto>;
  totalBuyPriceInYuan: FormControl<number>;
  totalBuyPriceInUah: FormControl<number>;
  amount: FormControl<number>;
  unitPrice: FormControl<number>;
  weight: FormControl<number>;
  trackId: FormControl<string>;
  name: FormControl<string>;
  comments: FormControl<string>;
  purchaseDate: FormControl<string>;
}

@Component({
  selector: 'app-edit',
  templateUrl: './procurement-edit.component.html'
})
export class ProcurementEditComponent implements OnInit {
  @Input() productBuy: TradesProductBuyDto;
  productForm: FormGroup<ProductFormValue>;
  products: TradesProductDto[] = [];

  constructor(
    public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private productService: TradesProductService
  ) {
    this.productForm = this.fb.group({
      id: new FormControl<string>(null),
      product: new FormControl<TradesProductDto | null>(null, Validators.required),
      parcelGroup: new FormControl<TradesParcelGroupDto>(null),
      totalBuyPriceInYuan: new FormControl<number>(0, [Validators.required, Validators.min(1)]),
      totalBuyPriceInUah: new FormControl<number>(0, [Validators.required, Validators.min(1)]),
      amount: new FormControl<number>(0, [Validators.required, Validators.min(1)]),
      unitPrice: new FormControl<number>(0),
      weight: new FormControl<number>(null),
      trackId: new FormControl<string>(''),
      name: new FormControl<string>('', Validators.required),
      comments: new FormControl<string>(''),
      purchaseDate: new FormControl<string>(DateUtils.now(), Validators.required)
    });

    this.product.valueChanges.subscribe(
      product => {
        if (product) {
          this.name.setValue(product.name);
        }
      }
    );

    this.amount.valueChanges.subscribe(
       amount => {
         if (this.name.value && amount > 0) {
           const suggestedName = amount + ' ' + this.name.value;
           this.name.setValue(suggestedName);
         }
       }
    )
  }

  ngOnInit(): void {
    if (this.productBuy) {
      this.productForm.setValue({... this.productBuy});
    }

    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getProducts().subscribe(
      products => {
        this.products = products;
      },
      error => {
        console.error('Error loading products:', error);
      }
    );
  }

  saveProduct(): void {
    if (this.productForm.valid) {
      const formValue = this.productForm.getRawValue(); // Use getRawValue() if you need to include disabled controls, else use value.
      this.activeModal.close(formValue);
    }
  }

  productId(t1: TradesProductDto, t2: TradesProductDto): boolean {
    return t1 && t2 ? t1.id === t2.id : t1 === t2;
  }

  get id(): FormControl {
  return this.productForm.get('id') as FormControl;
}

get product(): FormControl {
  return this.productForm.get('product') as FormControl;
}

get parcelGroup(): FormControl {
  return this.productForm.get('parcelGroup') as FormControl;
}

get totalBuyPriceInYuan(): FormControl {
  return this.productForm.get('totalBuyPriceInYuan') as FormControl;
}

get totalBuyPriceInUah(): FormControl {
  return this.productForm.get('totalBuyPriceInUah') as FormControl;
}

get amount(): FormControl {
  return this.productForm.get('amount') as FormControl;
}

get unitPrice(): FormControl {
  return this.productForm.get('unitPrice') as FormControl;
}

get weight(): FormControl {
  return this.productForm.get('weight') as FormControl;
}

get trackId(): FormControl {
  return this.productForm.get('trackId') as FormControl;
}

get name(): FormControl {
  return this.productForm.get('name') as FormControl;
}

get comments(): FormControl {
  return this.productForm.get('comments') as FormControl;
}

get purchaseDate(): FormControl {
  return this.productForm.get('purchaseDate') as FormControl;
}
}
