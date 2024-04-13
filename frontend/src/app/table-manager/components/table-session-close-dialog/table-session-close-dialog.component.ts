import {Component, Input, OnInit} from '@angular/core';
import {TableSession} from "../../model/model";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {Times} from "../../services/Times";

@Component({
  selector: 'app-table-session-close-dialog',
  templateUrl: './table-session-close-dialog.component.html',
  styleUrls: ['./table-session-close-dialog.component.scss']
})
export class TableSessionCloseDialogComponent implements OnInit {

  @Input() public tableSessions: TableSession[];
  public totalPaidAmount: number = 0;
  public totalPay: number = 0;
  public totalNeedToPay: number = 0;
  public return: number = 0;

  public form: FormGroup;

  constructor(private fb: FormBuilder,
              private activeModal: NgbActiveModal) {
  }

  public ngOnInit(): void {
    this.initForm();

    this.tableSessions.forEach(tableSession => {
      this.totalPaidAmount += tableSession.paidAmount;
      this.totalPay += tableSession.totalPay;
      this.totalNeedToPay += tableSession.needToPay;
      if (this.totalNeedToPay < 0) {
        this.return = Math.abs(this.totalNeedToPay);
      }
    });
  }

  private initForm() {
    this.form = this.fb.group({
      totalPaid: null,
      baidBy: null,
    });
  }

  public get totalPaid(): FormControl {
    return this.form?.get('totalPaid') as FormControl;
  }

  public get baidBy(): FormControl {
    return this.form?.get('baidBy') as FormControl;
  }

  public cancel(): void {
    this.activeModal.dismiss();
  }

  protected readonly Times = Times;
}
