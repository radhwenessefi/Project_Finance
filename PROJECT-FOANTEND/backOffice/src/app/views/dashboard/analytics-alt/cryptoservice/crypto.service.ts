import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CryptoService {
  private apiUrl = 'http://localhost:8082/PIDEV/crypto'; // URL de l'API

  constructor(private http: HttpClient) {}
 
  getCryptoData(symbol: string,range:string,start:string,end:string): Observable<any> {
    const url = `${this.apiUrl}/${symbol}/${range}/${start}/${end}`;
    return this.http.get(url);
  }
}
