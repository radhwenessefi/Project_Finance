import { Component, OnInit } from '@angular/core';
import { EventService } from '../../shared/services/Event/event.service';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrls: ['./event-list.component.scss'],
})
export class EventListComponent implements OnInit {
  events: any[] = []; // Liste des événements
  selectedEvent: any = null; // Événement sélectionné pour modification
  newEvent: any = {}; // Initialisation pour créer un nouvel événement

  constructor(private eventService: EventService) {}

  ngOnInit(): void {
    this.loadEvents();
  }

  // Charger tous les événements
  loadEvents(): void {
    this.eventService.getAllEvents().subscribe({
      next: (data) => {
        this.events = data; // Affecter les événements récupérés
      },
      error: (err) => {
        console.error('Erreur lors du chargement des événements:', err);
      },
    });
  }

  // Ajouter un nouvel événement
  addEvent(): void {
    this.eventService.createEvent(this.newEvent).subscribe({
      next: (event) => {
        this.events.push(event); // Ajouter à la liste locale
        this.newEvent = {}; // Réinitialiser le formulaire
      },
      error: (err) => {
        console.error('Erreur lors de la création de l\'événement:', err);
      },
    });
  }

  // Modifier un événement existant
  editEvent(event: any): void {
    this.selectedEvent = { ...event }; // Faire une copie pour modification
  }

  // Mettre à jour un événement
  updateEvent(): void {
    if (this.selectedEvent) {
      this.eventService.updateEvent(this.selectedEvent.id, this.selectedEvent).subscribe({
        next: (updatedEvent) => {
          const index = this.events.findIndex(e => e.id === updatedEvent.id);
          if (index > -1) this.events[index] = updatedEvent; // Mettre à jour dans la liste
          this.selectedEvent = null; // Réinitialiser la sélection
        },
        error: (err) => {
          console.error('Erreur lors de la mise à jour de l\'événement:', err);
        },
      });
    }
  }

  // Supprimer un événement
  deleteEvent(id: string): void {
    this.eventService.deleteEvent(id).subscribe({
      next: () => {
        this.events = this.events.filter(event => event.id !== id); // Retirer de la liste locale
      },
      error: (err) => {
        console.error('Erreur lors de la suppression de l\'événement:', err);
      },
    });
  }
}
