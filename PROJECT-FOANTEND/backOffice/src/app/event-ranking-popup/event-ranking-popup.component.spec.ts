import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventRankingPopupComponent } from './event-ranking-popup.component';

describe('EventRankingPopupComponent', () => {
  let component: EventRankingPopupComponent;
  let fixture: ComponentFixture<EventRankingPopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EventRankingPopupComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EventRankingPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
