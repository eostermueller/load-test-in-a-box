import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StartStopComponent } from './start-stop.component';

describe('StartStopComponent', () => {
  let component: StartStopComponent;
  let fixture: ComponentFixture<StartStopComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StartStopComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StartStopComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
