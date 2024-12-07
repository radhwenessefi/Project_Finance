import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root', // Fournit le service à toute l'application
})
export class EventService {
  private apiUrl = 'http://127.0.0.1:8082/PIDEV/api/events';
  constructor(private http: HttpClient) {}

  // Récupérer tous les événements
  getAllEvents(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  // Ajouter un nouvel événement
  createEvent(event: any): Observable<any> {
    return this.http.post(this.apiUrl, event);
  }

  // Supprimer un événement
  deleteEvent(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
