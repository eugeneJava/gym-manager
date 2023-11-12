import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PlaySessionEditComponent } from './play-session-edit.component';

describe('NewSessionFormComponent', () => {
  let component: PlaySessionEditComponent;
  let fixture: ComponentFixture<PlaySessionEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlaySessionEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlaySessionEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
