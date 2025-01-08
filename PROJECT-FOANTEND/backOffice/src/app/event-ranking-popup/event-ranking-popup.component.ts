import { CommonModule } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-event-ranking-popup',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './event-ranking-popup.component.html',
  styleUrl: './event-ranking-popup.component.scss'
})
export class EventRankingPopupComponent {
  topThree: any[] = [];
  remainingRankings: any[] = [];

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {}

  ngOnInit(): void {
    this.topThree = this.data.rankings.slice(0, 3);
    this.remainingRankings = this.data.rankings.slice(3);
  }
}
