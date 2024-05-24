import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SalesSimpleSellRacketFlowComponent } from './sales-simple-sell-racket-flow.component';

describe('SalesSimpleSellRacketFlowComponent', () => {
  let component: SalesSimpleSellRacketFlowComponent;
  let fixture: ComponentFixture<SalesSimpleSellRacketFlowComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SalesSimpleSellRacketFlowComponent]
    });
    fixture = TestBed.createComponent(SalesSimpleSellRacketFlowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
