import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkloadSelectionTypeComponent } from './workload-selection-type.component';

describe('WorkloadSelectionTypeComponent', () => {
  let component: WorkloadSelectionTypeComponent;
  let fixture: ComponentFixture<WorkloadSelectionTypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkloadSelectionTypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkloadSelectionTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
