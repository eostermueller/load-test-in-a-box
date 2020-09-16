import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkloadKeyComponent } from './workload-key.component';

describe('WorkloadKeyComponent', () => {
  let component: WorkloadKeyComponent;
  let fixture: ComponentFixture<WorkloadKeyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkloadKeyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkloadKeyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
