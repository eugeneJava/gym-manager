import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {TableService} from '../../services/table.service';
import {TableSession, Table} from '../../model/model';

@Component({
  selector: 'app-move-player-dialog',
  templateUrl: './move-player-dialog.component.html',
  styleUrls: ['./move-player-dialog.component.scss']
})
export class MovePlayerDialogComponent implements OnInit {

  public form: FormGroup;
  public tables: Table[];

  constructor(private fb: FormBuilder,
              private activeModal: NgbActiveModal,
              private tableService: TableService) {
  }

  ngOnInit(): void {
    this.tableService.getTables()
      .subscribe(tables => {
        this.tables = tables;
      });

    this.tableNumber.valueChanges.subscribe(tableNumber => {
      if (this.tables) {
        const selectedTable : Table = this.tables.find(table => table.number === tableNumber);
        this.rate.setValue(selectedTable.rate);
      }
    });


    this.form = this.fb.group({
      tableNumber: null
    });
  }

  public get tableNumber(): FormControl {
    return this.form.get('tableNumber') as FormControl;
  }

  public get rate(): FormControl {
    return this.form.get('rate') as FormControl;
  }

  public save(): void {
    const playSession : TableSession = this.form.value as TableSession;
    this.activeModal.close(playSession);
  }

  public cancel(): void {
    this.activeModal.dismiss();
  }
}
