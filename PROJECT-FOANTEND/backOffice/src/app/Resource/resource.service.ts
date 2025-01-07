import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ResourceService {
  private apiUrl = 'http://localhost:8082/PIDEV/api/resources'; 

  constructor(private http: HttpClient) {}
    getAllResourcess(): Observable<any[]> {
      return this.http.get<any[]>(`${this.apiUrl}`);
    }
    createResource(resource): Observable<any>{
      return this.http.post<any>(`${this.apiUrl}`,resource);
    }
}
