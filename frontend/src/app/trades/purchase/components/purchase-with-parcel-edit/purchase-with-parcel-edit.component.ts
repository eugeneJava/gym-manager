import {Component, Input, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, UntypedFormGroup, Validators} from '@angular/forms';
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
  productBuys: FormArray;
}

@Component({
  selector: 'app-edit',
  templateUrl: './purchase-with-parcel-edit.component.html',
  styleUrls: ['./purchase-with-parcel-edit.component.scss']
})
export class PurchaseWithParcelEdit implements OnInit {
  @Input() productBuy: TradesParcelGroupDto;
  productBuyForm: UntypedFormGroup;
  products: TradesProductDto[] = [];
  moreThanOneProduct: boolean = false;
  totalCalculatedForVerification: number = 0;
  yuanToUahRate: number = 0;

  constructor(
    public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private productService: TradesProductService
  ) {

  }

  ngOnInit(): void {
    this.productBuyForm = this.fb.group({
      id: new FormControl<string>(null),
      totalBuyPriceInUah: new FormControl<number>(0, [Validators.required, Validators.min(1)]),
      totalBuyPriceInYuan: new FormControl<number>(0,[Validators.required, Validators.min(1)]),
      weight: new FormControl<number>(null),
      trackId: new FormControl<string>('', Validators.required),
      name: new FormControl<string>('',  Validators.required),
      comments: new FormControl<string>(''),
      purchaseDate: new FormControl<string>(DateUtils.nowAsDate(), Validators.required),
      productBuys: this.fb.array([]),
      allProductsSameWeight: new FormControl<boolean>(true),
    });
    this.addNewProductBuy();

    this.productBuysGroups[0].get('product').valueChanges
      .subscribe(product => {
        this.name.setValue(this.formName(product.name, '1', product.name));
      });

    this.loadProducts();

    this.totalBuyPriceInUah.valueChanges.subscribe(
      value => {
        if (!this.moreThanOneProduct) {
           this.productBuysGroups[0].get('totalBuyPriceInUah').setValue(value);
        }
      }
    );

    /*this.product.valueChanges.subscribe(
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
    )*/


    if (this.productBuy) {
      this.productBuyForm.setValue({...this.productBuy});
    }
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

  private addNewProductBuy() {
    const productBuy = this.fb.group({
      product: new FormControl<TradesProductDto>(null, Validators.required),
      totalBuyPriceInUah: new FormControl<number>(0, [Validators.required, Validators.min(1)]),
      unitPriceInYuan: new FormControl<number>(0),
      amount: new FormControl<number>(1, [Validators.required, Validators.min(1)]),
      comments: new FormControl<string>('')
    });
    this.productBuys.push(productBuy);

    productBuy.get('amount').valueChanges.subscribe(
      amount => {
        let totalAmount = 0;
        this.productBuysGroups.forEach(buy => {
           totalAmount +=  buy.get('amount').value
        });

        this.name.setValue(this.formName(this.name.value, totalAmount + ''));
      }
    );
  }

  formName(currentName: string, firstPart, secondPart?):  string {
    const parts = currentName?.split('-',2)

    const newName = firstPart + '-' + (!parts[1] ? secondPart : parts[1])
    return newName;
  }

  saveProduct(): void {
      const formValue = this.productBuyForm.getRawValue(); // Use getRawValue() if you need to include disabled controls, else use value.
      this.activeModal.close(formValue);
  }

  productId(t1: TradesProductDto, t2: TradesProductDto): boolean {
    return t1 && t2 ? t1.id === t2.id : t1 === t2;
  }

  get id(): FormControl {
    return this.productBuyForm.get('id') as FormControl;
  }

  get product(): FormControl {
    return this.productBuyForm.get('product') as FormControl;
  }

  get parcelGroup(): FormControl {
    return this.productBuyForm.get('parcelGroup') as FormControl;
  }

  get totalBuyPriceInYuan(): FormControl {
    return this.productBuyForm.get('totalBuyPriceInYuan') as FormControl;
  }

  get totalBuyPriceInUah(): FormControl {
    return this.productBuyForm.get('totalBuyPriceInUah') as FormControl;
  }

  get amount(): FormControl {
    return this.productBuyForm.get('amount') as FormControl;
  }

  get unitPrice(): FormControl {
    return this.productBuyForm.get('unitPrice') as FormControl;
  }

  get weight(): FormControl {
    return this.productBuyForm.get('weight') as FormControl;
  }

  get trackId(): FormControl {
    return this.productBuyForm.get('trackId') as FormControl;
  }

  get name(): FormControl {
    return this.productBuyForm.get('name') as FormControl;
  }

  get comments(): FormControl {
    return this.productBuyForm.get('comments') as FormControl;
  }

  get purchaseDate(): FormControl {
    return this.productBuyForm.get('purchaseDate') as FormControl;
  }

  get productBuys(): FormArray {
    return this.productBuyForm.get('productBuys') as FormArray;
  }

  get productBuysGroups(): FormGroup[] {
    return this.productBuys.controls as FormGroup[];
  }

  addProductBuy() {
    this.addNewProductBuy();
    this.moreThanOneProduct = true;
  }

  recalculatePrices() {
    const totalUah: number = this.totalBuyPriceInUah.value;
    const totalYuan: number = this.totalBuyPriceInYuan.value;
    const rate = totalUah / totalYuan;

    this.yuanToUahRate = Math.round(rate * 100) / 100;

    this.productBuysGroups.forEach(group => {
      const amount : number = group.get('amount').value;
      const unitPriceInYuan: number = group.get('unitPriceInYuan').value;
      const totalInUah = Math.round(amount * unitPriceInYuan * rate);

      this.totalCalculatedForVerification += totalInUah;
      group.get('totalBuyPriceInUah').setValue(totalInUah);
    })
  }
}
