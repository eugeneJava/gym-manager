import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PurchaseStatisticsListComponent } from './purchase-statistics-list.component';

describe('PurchaseStatisticsListComponent', () => {
  let component: PurchaseStatisticsListComponent;
  let fixture: ComponentFixture<PurchaseStatisticsListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PurchaseStatisticsListComponent]
    });
    fixture = TestBed.createComponent(PurchaseStatisticsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
