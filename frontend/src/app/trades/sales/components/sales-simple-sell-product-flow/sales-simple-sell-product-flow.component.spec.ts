import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SalesSimpleSellProductFlowComponent } from './sales-simple-sell-product-flow.component';

describe('SalesSimpleSellRacketFlowComponent', () => {
  let component: SalesSimpleSellProductFlowComponent;
  let fixture: ComponentFixture<SalesSimpleSellProductFlowComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SalesSimpleSellProductFlowComponent]
    });
    fixture = TestBed.createComponent(SalesSimpleSellProductFlowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
