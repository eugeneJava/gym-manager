import {Component, Input, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {TradesTradesProductUnitService} from "../../services/trades-product-unit.service";
import {
  ProductsAvailableForSaleDto,
  SaleGroupType,
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
  editForm: FormGroup;
  productUnits: ProductsAvailableForSaleDto[] = [];

  constructor(
    public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private productUnitService: TradesTradesProductUnitService,
  ) {
    this.editForm = this.fb.group({
      id: [''],
      soldAt: [DateUtils.now(), [Validators.required]],
      comments: [''],
      type: [SaleGroupType.RACKET, [Validators.required]],
      productSales: this.fb.array([])
    });

    if(this.editForm.get('type').value === SaleGroupType.RACKET) {
      this.productSales.push(this.createProductSale());
      this.productSales.push(this.createProductSale());
    }
  }

  createProductSale(): FormGroup {
    const productSale = this.fb.group({
      sellPrice: ['', [Validators.required]],
      product: ['', [Validators.required]],
      productSaleGroupId: [''],
      amountToSell: [1, [Validators.required, Validators.min(1)]],
      suggestedPrice: ['']
    });
    return productSale;
  }

  ngOnInit(): void {
    this.loadProductUnits();
    this.loadProductSaleGroups();
  }

  loadProductUnits(): void {
    this.productUnitService.getAvailebleForSaleProducts().subscribe(
      data => {
        this.productUnits = data;
      },
      error => console.error('Error fetching product units', error)
    );
  }

  loadProductSaleGroups(): void {

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
