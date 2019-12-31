import { TestBed } from '@angular/core/testing';

import { SutLaunchStatusService } from './sut-launch-status.service';

describe('SutLaunchStatusService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SutLaunchStatusService = TestBed.get(SutLaunchStatusService);
    expect(service).toBeTruthy();
  });
});
