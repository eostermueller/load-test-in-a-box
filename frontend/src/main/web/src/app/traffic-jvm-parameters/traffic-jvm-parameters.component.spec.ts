import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrafficJvmParametersComponent } from './traffic-jvm-parameters.component';

describe('TrafficJvmParametersComponent', () => {
  let component: TrafficJvmParametersComponent;
  let fixture: ComponentFixture<TrafficJvmParametersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrafficJvmParametersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrafficJvmParametersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
