import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MatPaginationDemoComponent } from './mat-pagination-demo.component';

describe('MatPaginationDemoComponent', () => {
  let component: MatPaginationDemoComponent;
  let fixture: ComponentFixture<MatPaginationDemoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MatPaginationDemoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MatPaginationDemoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
