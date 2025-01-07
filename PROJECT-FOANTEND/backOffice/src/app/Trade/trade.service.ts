import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TradeService {
  private apiUrl = 'http://localhost:8082/PIDEV/api/trades'; // URL de ton backend

  constructor(private http: HttpClient) {}
  getTradesByUserId(userId): Observable<any[]> {
      return this.http.get<any[]>(`${this.apiUrl}/user/${userId}`);
    }
  createTrade(trade): Observable<any>{
    return this.http.post<any>(`${this.apiUrl}`,trade);
  }
}
