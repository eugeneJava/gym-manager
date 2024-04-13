import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TradesParcelDto, TradesParcelGroupDto } from "../../../model/trades-product.model";
import { TradesParcelGroupService } from "../service/trades-parcel-group.service";
import { TradesParcelEditComponent } from "../components/parcel-edit/trades-parcel-edit.component";
import { TradesParcelService } from "../service/trades-parcel.service";

@Component({
  selector: 'app-trades-parcel-container',
  templateUrl: './trades-parcel-container.component.html'
})
export class TradesParcelContainerComponent implements OnInit {
  parcels: TradesParcelDto[] = [];
  parcelGroupsWithoutParcel: TradesParcelGroupDto[] = [];

  constructor(
    private tradesParcelService: TradesParcelService,
    private parcelGroupService: TradesParcelGroupService,
    private modalService: NgbModal
  ) { }

  ngOnInit(): void {
    this.loadParcels();
    this.loadParcelGroupsWithoutParcel();
  }

  loadParcels(): void {
    this.tradesParcelService.getAll().subscribe(parcels => {
      this.parcels = parcels;
    });
  }

  loadParcelGroupsWithoutParcel(): void {
    this.parcelGroupService.getParcelGroupsWithoutParcel().subscribe(parcelGroups => {
      this.parcelGroupsWithoutParcel = parcelGroups;
    });
  }

  addParcel(): void {
    const modalRef = this.modalService.open(TradesParcelEditComponent);
    modalRef.componentInstance.parcelGroupsWithoutParcel = this.parcelGroupsWithoutParcel;
    modalRef.result.then((result: TradesParcelDto) => {
      if (result) {
        this.createParcel(result);
      }
    }, (reason) => {
      // Handle close reason if needed
    });
  }

  editParcel(parcel: TradesParcelDto): void {
    const modalRef = this.modalService.open(TradesParcelEditComponent);
    modalRef.componentInstance.parcel = parcel; // Pass the selected parcel to the component
    modalRef.componentInstance.parcelGroupsWithoutParcel = this.parcelGroupsWithoutParcel;
    modalRef.result.then((result: TradesParcelDto) => {
      if (result) {
        this.updateParcel(result);
      }
    }, (reason) => {
      // Handle close reason if needed
    });
  }

  createParcel(parcel: TradesParcelDto): void {
    this.tradesParcelService.create(parcel).subscribe(() => {
      this.loadParcels();
      this.loadParcelGroupsWithoutParcel();
    });
  }

  updateParcel(parcel: TradesParcelDto): void {
    this.tradesParcelService.update(parcel).subscribe(() => {
      this.loadParcels();
      this.loadParcelGroupsWithoutParcel();
    });
  }
}

