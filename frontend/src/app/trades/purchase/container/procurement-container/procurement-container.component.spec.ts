import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcurementContainerComponent } from './procurement-container.component';

describe('ProcurementContainerComponent', () => {
  let component: ProcurementContainerComponent;
  let fixture: ComponentFixture<ProcurementContainerComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProcurementContainerComponent]
    });
    fixture = TestBed.createComponent(ProcurementContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
