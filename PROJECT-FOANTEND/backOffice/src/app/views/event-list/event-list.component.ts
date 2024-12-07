import { Component, OnInit } from '@angular/core';
import { EventService } from '../../services/event.service';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrls: ['./event-list.component.css'],
})
export class EventListComponent implements OnInit {
  events: any[] = []; // Initialisation de la liste

  constructor(private eventService: EventService) {}

  ngOnInit(): void {
    this.eventService.getAllEvents().subscribe({
      next: (data) => {
        this.events = data; // Assigne les événements récupérés
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des événements', err);
      },
    });
  }
}
