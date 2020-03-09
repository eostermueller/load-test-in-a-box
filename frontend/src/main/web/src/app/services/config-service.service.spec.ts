import { TestBed } from '@angular/core/testing';

import { ConfigServiceService } from './config-service.service';

describe('ConfigServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ConfigServiceService = TestBed.get(ConfigServiceService);
    expect(service).toBeTruthy();
  });
});
