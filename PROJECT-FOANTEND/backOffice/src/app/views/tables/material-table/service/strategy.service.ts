import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StrategyService {

  private apiUrl = 'http://localhost:8082/PIDEV/get_stat'; // Update this with your backend API URL

  constructor(private http: HttpClient) { }

  // Call the API to get strategy stats
  getStrategyStats(cash: number, margin: number, stratNum: number): Observable<string> {
    const params = new HttpParams()
      .set('cash', cash.toString())
      .set('margin', margin.toString())
      .set('stratNum', stratNum.toString());

    return this.http.get<string>(this.apiUrl, { params });
  }
}
