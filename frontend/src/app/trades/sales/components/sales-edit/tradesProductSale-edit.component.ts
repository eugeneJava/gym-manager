import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {TradesTradesProductUnitService} from "../../services/trades-product-unit.service";
import {
  ProductsAvailableForSaleDto,
  TradesProductDto,
  TradesProductSaleDto
} from "../../../../model/trades-product.model";
import {DateUtils} from "../../../../model/date-utils";

@Component({
  selector: 'app-trades-product-sale-edit',
  templateUrl: './tradesProductSale-edit.component.html'
})
export class TradesProductSaleEditComponent implements OnInit {
  @Input() sale: TradesProductSaleDto;
  editForm: FormGroup;
  productUnits: ProductsAvailableForSaleDto[] = []; // Adjust the type accordingly
  productSaleGroups: any[] = []; // Adjust the type accordingly

  constructor(
    public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private productUnitService: TradesTradesProductUnitService,
  ) {
    this.editForm = this.fb.group({
      sellPrice: ['', [Validators.required]],
      product: ['', [Validators.required]],
      productSaleGroupId: [''],
      soldAt: [DateUtils.now(), [Validators.required]],
      amountToSell: [1, [Validators.required, Validators.min(1)]],
      comments: [''],
      suggestedPrice: ['']
    });
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

  save(): void {
    if (this.editForm.valid) {
      this.activeModal.close(this.editForm.value);
    }
  }

  productId(t1: TradesProductDto, t2: TradesProductDto): boolean {
    return t1 && t2 ? t1.id === t2.id : t1 === t2;
  }
}
