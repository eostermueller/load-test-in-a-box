import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UseCaseCardComponent } from './use-case-card.component';

describe('UseCaseCardComponent', () => {
  let component: UseCaseCardComponent;
  let fixture: ComponentFixture<UseCaseCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UseCaseCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UseCaseCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
