import {Component, Input} from '@angular/core';
import {
  ProductsAvailableForSaleDto,
  RacketSellDto,
  TradesProductCategoryDto,
  TradesProductDto,
  TradesProductSaleDto
} from "../../../../model/trades-product.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {TradesTradesProductUnitService} from "../../services/trades-product-unit.service";
import {TradesProductSaleService} from "../../services/tradesProductSale.service";
import {finalize} from "rxjs";
import {DateUtils} from "../../../../model/date-utils";

@Component({
  selector: 'app-sales-simple-sell-racket-flow',
  templateUrl: './sales-simple-sell-product-flow.component.html',
  styleUrls: ['./sales-simple-sell-product-flow.component.scss']
})
export class SalesSimpleSellProductFlowComponent {
  @Input() sale: TradesProductSaleDto;
  editForm: FormGroup;
  productUnits: ProductsAvailableForSaleDto[] = [];
  categories: TradesProductCategoryDto[] = [];
  productUnitsOfSelectedCategory: ProductsAvailableForSaleDto[] = [];
  success: boolean = false;
  error: boolean = false;

  public steps: FlowStepsForProduct[] = [
    FlowStepsForProduct.SELECT_CATEGORY,
    FlowStepsForProduct.SELECT_PRODUCT,
    FlowStepsForProduct.SELECT_AMOUNT_AND_PRICE,
    FlowStepsForProduct.CONFIRM,
    FlowStepsForProduct.FINAL];
  public currentStepIndex: number = 0;
  public currentStep: FlowStepsForProduct;
  public currentStepValid: boolean = false;

  constructor(
    private fb: FormBuilder,
    private productUnitService: TradesTradesProductUnitService,
    private productSaleService: TradesProductSaleService
  ) {
    this.editForm = this.fb.group({
      category: [null, Validators.required],
      product: [null, Validators.required],
      amount: [1, Validators.min(1)],
      sellPrice: [null, Validators.min(1)]
    });
  }

  ngOnInit(): void {
    this.currentStep = this.steps[this.currentStepIndex];
    this.stepSelected();

    this.editForm.get('category').valueChanges.subscribe((category: TradesProductCategoryDto) => {
      this.productUnitsOfSelectedCategory = this.productUnits.filter(p => p.product.category.id === category.id);
      if (category) {
        this.currentStepValid = true;
      }
    });


    this.editForm.get('product').valueChanges.subscribe((sale: ProductsAvailableForSaleDto) => {
      this.editForm.get('sellPrice').setValue(sale.product.recommendedPrice);
      if (sale) {
        this.currentStepValid = true;
      }
    });

    this.editForm.get('amount').valueChanges.subscribe((amount: number) => {
      if (amount > 0) {
        this.currentStepValid = true;
      } else {
        this.currentStepValid = false;
      }
    });

    this.editForm.get('sellPrice').valueChanges.subscribe((salePrice: number) => {
      if (salePrice > 0) {
        this.currentStepValid = true;
      } else {
        this.currentStepValid = false;
      }
    });

    this.loadProductUnits();
  }

  public getTotalPrice(): number {
    return this.editForm.get('sellPrice')?.value * this.editForm.get('amount')?.value;
  }

  loadProductUnits(): void {
    this.productUnitService.getAvailebleForSaleProducts().subscribe(
      data => {
        this.productUnits = data;

        this.productUnits.forEach(unit => {
          const containsCategory = this.categories.some(c => c.id === unit.product.category.id);
          if (!containsCategory) {
            this.categories.push(unit.product.category);
          }
        });
      },
      error => console.error('Error fetching product units', error)
    );
  }

  save(): void {
    const product: TradesProductSaleDto = {
      product: this.editForm.get('product').value.product,
      amountToSale: this.editForm.get('amount').value.product,
      sellPrice: this.editForm.get('sellPrice').value.product,
      soldAt: 0,
    } as TradesProductSaleDto;

    this.productSaleService.createTradesProductSale(product)
      .pipe(finalize(() => {
        this.nextStep();
      }))
      .subscribe(() => {
          this.success = true;
        }, er => this.error = true
      )
  }

  productId(t1: TradesProductDto, t2: TradesProductDto): boolean {
    return t1 && t2 ? t1.id === t2.id : t1 === t2;
  }

  nextStep(): void {
    this.currentStep = this.steps[++this.currentStepIndex];
    this.stepSelected();
  }

  back(): void {
    this.currentStep = this.steps[--this.currentStepIndex];
  }

  stepSelected(): void {
    this.currentStepValid = false;
    if (this.currentStep === FlowStepsForProduct.SELECT_AMOUNT_AND_PRICE) {
      const sellPrice: number = this.editForm.get('sellPrice')?.value
      const amount: number = this.editForm.get('amount')?.value
      if (sellPrice > 0 && amount > 0) {
        this.currentStepValid = true;
      }
    }

    if (this.currentStep === FlowStepsForProduct.CONFIRM) {
      this.currentStepValid = true;
    }
  }

  protected readonly FlowStepsForProduct = FlowStepsForProduct;

  reload() {
    window.location.reload()
  }

  close() {
    window.close();
  }
}

export enum FlowStepsForProduct {
  SELECT_CATEGORY = 'SELECT_CATEGORY',
  SELECT_PRODUCT = 'SELECT_PRODUCT',
  SELECT_AMOUNT_AND_PRICE = 'SELECT_AMOUNT_AND_PRICE',
  CONFIRM = 'CONFIRM',
  FINAL = 'FINAL',
}
