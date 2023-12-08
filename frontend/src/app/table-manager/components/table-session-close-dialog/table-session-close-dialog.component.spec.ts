import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableSessionCloseDialogComponent } from './table-session-close-dialog.component';

describe('TableSessionCloseDialogComponent', () => {
  let component: TableSessionCloseDialogComponent;
  let fixture: ComponentFixture<TableSessionCloseDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TableSessionCloseDialogComponent]
    });
    fixture = TestBed.createComponent(TableSessionCloseDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
