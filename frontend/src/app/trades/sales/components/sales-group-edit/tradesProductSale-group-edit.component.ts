import {Component, Input, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, UntypedFormBuilder, UntypedFormGroup, Validators} from '@angular/forms';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {TradesTradesProductUnitService} from "../../services/trades-product-unit.service";
import {
  ProductsAvailableForSaleDto,
  SaleGroupType, TradesProductCategory,
  TradesProductDto,
  TradesProductSaleGroupDto
} from "../../../../model/trades-product.model";
import {DateUtils} from "../../../../model/date-utils";

@Component({
  selector: 'app-trades-product-sale-edit',
  templateUrl: './tradesProductSale-group-edit.component.html'
})
export class TradesProductSaleGroupEditComponent implements OnInit {
  @Input() saleGroup: TradesProductSaleGroupDto;
  editForm: UntypedFormGroup;
  productUnits: ProductsAvailableForSaleDto[] = [];
  rubbers: ProductsAvailableForSaleDto[] = [];
  blades: ProductsAvailableForSaleDto[] = [];

  constructor(
    public activeModal: NgbActiveModal,
    private fb: UntypedFormBuilder,
    private productUnitService: TradesTradesProductUnitService,
  ) {
    this.editForm = this.fb.group({
      id: [''],
      soldAt: [DateUtils.nowAsDate(), [Validators.required]],
      comments: [''],
      type: [SaleGroupType.RACKET, [Validators.required]],
      productSales: this.fb.array([])
    });

    if(this.editForm.get('type').value === SaleGroupType.RACKET) {
      this.productSales.push(this.createProductSale(1));
      this.productSales.push(this.createProductSale(2));
    }
  }

  createProductSale(amountToSell: number): FormGroup {
    const productSale = this.fb.group({
      sellPrice: ['', [Validators.required]],
      product: ['', [Validators.required]],
      productSaleGroupId: [''],
      amountToSell: [amountToSell, [Validators.required, Validators.min(1)]],
      suggestedPrice: ['']
    });

    productSale.get('product').valueChanges.subscribe((product: TradesProductDto) => {
      productSale.get('sellPrice').setValue(product?.recommendedPrice);
    });
    return productSale;
  }

  ngOnInit(): void {
    this.loadProductUnits();
  }

  loadProductUnits(): void {
    this.productUnitService.getAvailebleForSaleProducts().subscribe(
      data => {
        this.productUnits = data;
        this.rubbers = this.productUnits.filter(p => p.product.category.id === TradesProductCategory.RUBBER);
        this.blades = this.productUnits.filter(p => p.product.category.id === TradesProductCategory.BLADE);
      },
      error => console.error('Error fetching product units', error)
    );
  }


  get productSales(): FormArray {
    return this.editForm.get('productSales') as FormArray;
  }

  get productSaleGroups(): FormGroup[] {
    return this.productSales.controls as FormGroup[];
  }

  save(): void {
    if (this.editForm.valid) {
      this.activeModal.close(this.editForm.value);
    }
  }

  productId(t1: TradesProductDto, t2: TradesProductDto): boolean {
    return t1 && t2 ? t1.id === t2.id : t1 === t2;
  }
}
