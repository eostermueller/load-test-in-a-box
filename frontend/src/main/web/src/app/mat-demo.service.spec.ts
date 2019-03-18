import { TestBed } from '@angular/core/testing';

import { MatDemoService } from './mat-demo.service';

describe('MatDemoService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: MatDemoService = TestBed.get(MatDemoService);
    expect(service).toBeTruthy();
  });
});
