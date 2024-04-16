import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators, FormArray} from '@angular/forms';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {TradesParcelDto, TradesParcelGroupDto} from "../../../../model/trades-product.model";

@Component({
  selector: 'app-trades-parcel-edit',
  templateUrl: './trades-parcel-edit.component.html',
  styleUrls: ['./trades-parcel-edit.component.scss']
})
export class TradesParcelEditComponent implements OnInit {
  @Input() parcel: TradesParcelDto;
  @Input() parcelGroupsWithoutParcel: TradesParcelGroupDto[] = [];

  parcelForm: FormGroup;

  constructor(public activeModal: NgbActiveModal, private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.parcelForm = this.fb.group({
      id: [null],
      weight: [null, Validators.required],
      deliveryPrice: [null],
      startedDeliveryAt: [null, Validators.required],
      deliveredAt: [''],
      deliveryType: [null, Validators.required],
      comments: [''],
      name: [''],
      parcelGroups: this.fb.array([])
    });

    if (this.parcel) {
      this.setParcelGroups();
      this.parcelForm.patchValue(this.parcel);
    } else {
      this.parcelGroupsWithoutParcel?.forEach(pg =>
        this.parcelGroups.push(this.productGroupControl(pg, false)));
    }
  }

  private productGroupControl(pg: TradesParcelGroupDto, selected: boolean) {
    const purchaseFormGroup = this.fb.group({
      selected: selected,
      product: pg,
      weight: [null],
    });

    if (!pg.trackId) {
        purchaseFormGroup.disable()
    }

    return purchaseFormGroup;
  }

  get parcelGroups(): FormArray {
    return this.parcelForm.get('parcelGroups') as FormArray;
  }

  get parcelGroupsGroups(): FormGroup[] {
    return this.parcelGroups.controls as FormGroup[];
  }

  private setParcelGroups(): void {
    this.parcelGroupsWithoutParcel?.forEach((pg, index) => {
      const selected = this.parcel.parcelGroups?.some(p => p.id === pg.id);
      this.parcelGroups.push(this.productGroupControl(pg, selected));
    });
  }

  save(): void {
    if (this.parcelForm.valid) {
      const selectedParcelGroups: TradesParcelGroupDto[] = [];
      this.parcelGroups.controls.forEach(parcelGroup => {
        const selected = parcelGroup.get('selected').value;
        const product = parcelGroup.get('product').value;
        if (selected) {

          const group = this.parcelGroupsWithoutParcel.find(pg => pg.id === product.id);
          const weight = parcelGroup.get('weight').value;
          group.weight = weight;
          selectedParcelGroups.push(group);
        }
      });
      const result = {
        ...this.parcelForm.value,
        parcelGroups: selectedParcelGroups
      };
      this.activeModal.close(result);
    }
  }

  cancel(): void {
    this.activeModal.dismiss();
  }
}
