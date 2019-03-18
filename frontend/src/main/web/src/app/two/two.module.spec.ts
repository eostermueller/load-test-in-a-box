import { TwoModule } from './two.module';

describe('TwoModule', () => {
  let twoModule: TwoModule;

  beforeEach(() => {
    twoModule = new TwoModule();
  });

  it('should create an instance', () => {
    expect(twoModule).toBeTruthy();
  });
});
