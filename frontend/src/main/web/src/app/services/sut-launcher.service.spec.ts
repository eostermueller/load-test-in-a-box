import { TestBed } from '@angular/core/testing';

import { SutLauncherService } from './sut-launcher.service';

describe('SutLauncherService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SutLauncherService = TestBed.get(SutLauncherService);
    expect(service).toBeTruthy();
  });
});
