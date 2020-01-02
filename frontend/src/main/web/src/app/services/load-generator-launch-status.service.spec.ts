import { TestBed } from '@angular/core/testing';

import { LoadGeneratorLaunchStatusService } from './load-generator-launch-status-service';

describe('LoadGeneratorLaunchStatusServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: LoadGeneratorLaunchStatusService = TestBed.get(LoadGeneratorLaunchStatusService);
    expect(service).toBeTruthy();
  });
});
