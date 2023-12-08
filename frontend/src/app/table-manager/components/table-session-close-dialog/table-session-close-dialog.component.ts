import {Component, Input} from '@angular/core';
import {TableSession} from "../../model/model";
import {FormBuilder} from "@angular/forms";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {Times} from "../../services/Times";

@Component({
  selector: 'app-table-session-close-dialog',
  templateUrl: './table-session-close-dialog.component.html',
  styleUrls: ['./table-session-close-dialog.component.scss']
})
export class TableSessionCloseDialogComponent {
   @Input() public tableSessions: TableSession[];

  constructor(private fb: FormBuilder,
              private activeModal: NgbActiveModal) {
  }

  public cancel(): void {
    this.activeModal.dismiss();
  }

  protected readonly Times = Times;
}
