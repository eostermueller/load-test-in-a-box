import { TestBed } from '@angular/core/testing';

import { JMeterLauncherService } from './jmeter-launcher.service';

describe('JMeterLauncherService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: JMeterLauncherService = TestBed.get(JMeterLauncherService);
    expect(service).toBeTruthy();
  });
});
