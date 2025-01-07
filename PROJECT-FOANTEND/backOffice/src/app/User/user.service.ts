import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8082/PIDEV/api/users'; // URL de ton backend

  constructor(private http: HttpClient) {}
    getAllEvents(): Observable<any[]> {
      return this.http.get<any[]>(`${this.apiUrl}/all`);
    }
}
