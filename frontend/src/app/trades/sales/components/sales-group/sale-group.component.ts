import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormArray, FormGroup, UntypedFormBuilder, UntypedFormGroup, Validators} from '@angular/forms';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {
  ProductsAvailableForSaleDto, TradesProductBuyDto, TradesProductCategory,
  TradesProductDto,
  TradesProductSaleGroupDto
} from "../../../../model/trades-product.model";

@Component({
  selector: 'sale-group',
  templateUrl: './sale-group.component.html'
})
export class SaleGroupComponent implements OnInit {
  @Input() saleGroup: TradesProductSaleGroupDto;
  @Input() parentForm: UntypedFormGroup;@Input() productSale: UntypedFormGroup;
  @Input() productSalesArray: FormArray;
  @Input() amountToSell: number;
  @Input() productUnits: ProductsAvailableForSaleDto[];
  @Input() category: TradesProductCategory;

  @Output() productSelected: EventEmitter<TradesProductBuyDto> = new EventEmitter();

  constructor(
    public activeModal: NgbActiveModal,
    private fb: UntypedFormBuilder
  ) {}

  ngOnInit(): void {
    this.productSalesArray.push(this.createProductSale(this.amountToSell));
  }

  createProductSale(amountToSell: number): FormGroup {
    this.productSale = this.fb.group({
      sellPrice: ['', [Validators.required]],
      product: ['', [Validators.required]],
      productSaleGroupId: [''],
      amountToSell: [amountToSell, [Validators.required, Validators.min(1)]],
      suggestedPrice: ['']
    });

    this.productSale.get('product').valueChanges.subscribe((product: TradesProductDto) => {
      this.productSale.get('sellPrice').setValue(product?.recommendedPrice);
    });

    return this.productSale;
  }

  productId(t1: TradesProductDto, t2: TradesProductDto): boolean {
    return t1 && t2 ? t1.id === t2.id : t1 === t2;
  }
}
