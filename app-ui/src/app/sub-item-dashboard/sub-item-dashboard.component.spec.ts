import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubItemDashboardComponent } from './sub-item-dashboard.component';

describe('SubItemDashboardComponent', () => {
  let component: SubItemDashboardComponent;
  let fixture: ComponentFixture<SubItemDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubItemDashboardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubItemDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
