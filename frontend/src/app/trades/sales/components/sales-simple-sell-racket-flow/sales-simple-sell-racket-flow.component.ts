import {Component, Input} from '@angular/core';
import {
  ProductsAvailableForSaleDto,
  RacketSellDto,
  TradesProductCategory,
  TradesProductDto,
  TradesProductSaleDto
} from "../../../../model/trades-product.model";
import {FormBuilder, FormGroup} from "@angular/forms";
import {TradesTradesProductUnitService} from "../../services/trades-product-unit.service";
import {TradesProductSaleService} from "../../services/tradesProductSale.service";
import {finalize} from "rxjs";

@Component({
  selector: 'app-sales-simple-sell-racket-flow',
  templateUrl: './sales-simple-sell-racket-flow.component.html',
  styleUrls: ['./sales-simple-sell-racket-flow.component.scss']
})
export class SalesSimpleSellRacketFlowComponent {
  @Input() sale: TradesProductSaleDto;
  editForm: FormGroup;
  productUnits: ProductsAvailableForSaleDto[] = [];
  rubbers: ProductsAvailableForSaleDto[] = [];
  blades: ProductsAvailableForSaleDto[] = [];
  recommendedPrice: number = 0;
  success: boolean = false;
  error: boolean = false;

  public steps: FlowSteps[] = [FlowSteps.SELECT_BLADE, FlowSteps.SELCT_RUBBER1, FlowSteps.SELCT_RUBBER2, FlowSteps.SET_PRICE, FlowSteps.CONFIRM, FlowSteps.FINAL];
  public currentStepIndex: number = 0;
  public currentStep: FlowSteps;
  public currentStepValid: boolean = false;

  constructor(
    private fb: FormBuilder,
    private productUnitService: TradesTradesProductUnitService,
    private productSaleService: TradesProductSaleService
  ) {
   this.editForm = this.fb.group({
      blade: [null],
      rubber1: [null],
      rubber2: [null],
      sellPrice: [null]
    });
  }

  ngOnInit(): void {
    this.currentStep = this.steps[this.currentStepIndex];
    this.stepSelected();

    this.editForm.get('blade').valueChanges.subscribe((sale: ProductsAvailableForSaleDto) => {
      this.recommendedPrice += sale.product.recommendedPrice;
      this.editForm.get('sellPrice').setValue(this.recommendedPrice);
      if (sale) {
        this.currentStepValid = true;
      }
    });

    this.editForm.get('rubber1').valueChanges.subscribe((sale: ProductsAvailableForSaleDto) => {
      this.recommendedPrice += sale.product.recommendedPrice;
      this.editForm.get('sellPrice').setValue(this.recommendedPrice);
      if (sale) {
        this.currentStepValid = true;

        const rubbersOfThisType = this.rubbers.find(r => r.product.id !== sale.product.id)?.amountToSell;

        if (rubbersOfThisType === 1) {
          //remove the other rubber of this type
          this.rubbers = this.rubbers.filter(r => r.product.id !== sale.product.id);
        }
        }
    });

    this.editForm.get('rubber2').valueChanges.subscribe((sale: ProductsAvailableForSaleDto) => {
      this.recommendedPrice += sale.product.recommendedPrice;
      this.editForm.get('sellPrice').setValue(this.recommendedPrice);
      if (sale) {
        this.currentStepValid = true;
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

 save(): void {
   const racket: RacketSellDto = {
      blade: this.editForm.get('blade').value.product,
      rubber1: this.editForm.get('rubber1').value.product,
      rubber2: this.editForm.get('rubber2').value.product,
      sellPrice: this.editForm.get('sellPrice').value,
   }
   this.productSaleService.sellRacket(racket)
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
    if (this.currentStep === FlowSteps.SELECT_BLADE) {
      //this.editForm.get('blade').addValidators(Validators.required);
    }

    if (this.currentStep === FlowSteps.SELCT_RUBBER1) {

    }

    if (this.currentStep === FlowSteps.SELCT_RUBBER2) {
      //this.editForm.get('rubber2').addValidators(Validators.required);
    }

    if (this.currentStep === FlowSteps.SET_PRICE) {
      const sellPrice: number = this.editForm.get('sellPrice')?.value
      if (sellPrice > 0) {
        this.currentStepValid = true;
      }
    }

    if (this.currentStep === FlowSteps.CONFIRM) {
      this.currentStepValid = true;
    }
  }

  protected readonly FlowSteps = FlowSteps;

  reload() {
    window.location.reload()
  }

  close() {
    window.close();
  }
}

export enum FlowSteps {
 SELECT_BLADE = 'SELECT_BLADE',
 SELCT_RUBBER1 = 'SELCT_RUBBER1',
 SELCT_RUBBER2 = 'SELCT_RUBBER2',
 SET_PRICE = 'SET_PRICE',
 CONFIRM = 'CONFIRM',
 FINAL = 'FINAL',
}
