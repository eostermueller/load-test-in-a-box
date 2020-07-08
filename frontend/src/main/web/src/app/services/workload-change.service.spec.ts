import { TestBed } from '@angular/core/testing';

import { WorkloadChangeService } from './workload-change.service';

describe('WorkloadChangeService', () => {
  let service: WorkloadChangeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WorkloadChangeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
