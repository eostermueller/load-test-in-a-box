import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DemoCardComponent } from './demo-card.component';

describe('DemoCardComponent', () => {
  let component: DemoCardComponent;
  let fixture: ComponentFixture<DemoCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DemoCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DemoCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
