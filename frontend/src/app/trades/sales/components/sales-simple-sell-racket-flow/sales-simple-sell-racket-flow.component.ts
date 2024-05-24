import {Component, Input} from '@angular/core';
import {
  ProductsAvailableForSaleDto,
  TradesProductCategory,
  TradesProductDto,
  TradesProductSaleDto
} from "../../../../model/trades-product.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {TradesTradesProductUnitService} from "../../services/trades-product-unit.service";

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
  productSaleGroups: any[] = []; // Adjust the type accordingly

  public steps: FlowSteps[] = [FlowSteps.SELECT_BLADE, FlowSteps.SELCT_RUBBER1, FlowSteps.SELCT_RUBBER2, FlowSteps.SET_PRICE, FlowSteps.CONFIRM];
  public currentStepIndex: number = 0;
  public currentStep: FlowSteps = this.steps[0];

  constructor(
    private fb: FormBuilder,
    private productUnitService: TradesTradesProductUnitService
  ) {
   this.editForm = this.fb.group({
      blade: ['', [Validators.required]],
      rubber1: ['', [Validators.required]],
      rubber2: ['', [Validators.required]],
      sellPrice: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
    this.currentStep = this.steps[this.currentStepIndex];
/*    this.editForm.get('product').valueChanges.subscribe((product: TradesProductDto) => {
      this.editForm.get('sellPrice').setValue(product?.recommendedPrice);
    });*/

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
    if (this.editForm.valid) {
     // this.activeModal.close(this.editForm.value);
    }
  }

  productId(t1: TradesProductDto, t2: TradesProductDto): boolean {
    return t1 && t2 ? t1.id === t2.id : t1 === t2;
  }

  nextStep(): void {
    this.currentStepIndex++;
    this.currentStep = this.steps[this.currentStepIndex];
  }

  protected readonly FlowSteps = FlowSteps;
}

export enum FlowSteps {
 SELECT_BLADE = 'SELECT_BLADE',
 SELCT_RUBBER1 = 'SELCT_RUBBER1',
 SELCT_RUBBER2 = 'SELCT_RUBBER2',
 SET_PRICE = 'SET_PRICE',
 CONFIRM = 'CONFIRM',
}
