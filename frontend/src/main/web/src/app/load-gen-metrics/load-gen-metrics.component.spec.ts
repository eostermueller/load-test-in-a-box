import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadGenMetricsComponent } from './load-gen-metrics.component';

describe('LoadGenMetricsComponent', () => {
  let component: LoadGenMetricsComponent;
  let fixture: ComponentFixture<LoadGenMetricsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoadGenMetricsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadGenMetricsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
