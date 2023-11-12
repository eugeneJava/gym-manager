import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TableSchedulerComponent } from './table-scheduler.component';

describe('TableSchedulerComponent', () => {
  let component: TableSchedulerComponent;
  let fixture: ComponentFixture<TableSchedulerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TableSchedulerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TableSchedulerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
