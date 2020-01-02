import { TestBed } from '@angular/core/testing';

import { LoadGeneratorLauncherService } from './load-generator-launcher.service';

describe('LoadGeneratorLauncherService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: LoadGeneratorLauncherService = TestBed.get(LoadGeneratorLauncherService);
    expect(service).toBeTruthy();
  });
});
