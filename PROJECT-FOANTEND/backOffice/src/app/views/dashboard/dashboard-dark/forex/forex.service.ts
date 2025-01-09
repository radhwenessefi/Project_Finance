import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Result {
  volume: number;
  volumeWeightedAverage: number;
  openPrice: number;
  closePrice: number;
  highPrice: number;
  lowPrice: number;
  timestamp: number;
  tradeCount: number;
}

export interface ApiResponseForex {
  requestId: string;
  ticker: string;
  queryCount: number;
  resultsCount: number;
  adjusted: boolean;
  results: Result[];
  status: string;
  count: number;
}

@Injectable({
  providedIn: 'root'
})
export class ForexService {

  private apiUrl = 'http://localhost:8082/PIDEV/forex'; // Changez l'URL selon votre configuration

  constructor(private http: HttpClient) { }

  // Méthode pour récupérer les données Forex en temps réel
  getForexData(pair: string, range: string, dateStart: string, dateEnd: string): Observable<ApiResponseForex> {
    const url = `${this.apiUrl}/${pair}/${range}/${dateStart}/${dateEnd}`;
    return this.http.get<ApiResponseForex>(url);
  }
}
