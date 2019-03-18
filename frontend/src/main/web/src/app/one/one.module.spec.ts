import { OneModule } from './one.module';

describe('OneModule', () => {
  let oneModule: OneModule;

  beforeEach(() => {
    oneModule = new OneModule();
  });

  it('should create an instance', () => {
    expect(oneModule).toBeTruthy();
  });
});
