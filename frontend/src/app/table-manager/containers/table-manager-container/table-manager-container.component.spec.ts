import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TableManagerContainerComponent } from './table-manager-container.component';

describe('TableManagerContainerComponent', () => {
  let component: TableManagerContainerComponent;
  let fixture: ComponentFixture<TableManagerContainerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TableManagerContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TableManagerContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
