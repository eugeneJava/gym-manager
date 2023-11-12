import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MovePlayerDialogComponent } from './move-player-dialog.component';

describe('MovePlayerDialogComponent', () => {
  let component: MovePlayerDialogComponent;
  let fixture: ComponentFixture<MovePlayerDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MovePlayerDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MovePlayerDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
