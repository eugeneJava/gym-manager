import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TradesMainViewComponent } from './trades-main-view.component';

describe('TradesMainViewComponent', () => {
  let component: TradesMainViewComponent;
  let fixture: ComponentFixture<TradesMainViewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TradesMainViewComponent]
    });
    fixture = TestBed.createComponent(TradesMainViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
