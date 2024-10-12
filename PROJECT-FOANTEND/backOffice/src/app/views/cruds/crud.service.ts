import { Injectable } from '@angular/core';

import { UserDB } from '../../shared/inmemory-db/users';
import { Observable, of } from 'rxjs';
import { delay } from 'rxjs/operators';
import { PortfolioService } from 'app/views/Portfolio-Service/portfolio.Service';
import { Inject } from '@angular/core';
import { get } from 'http';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class CrudService {
  items: any[];
  data: any;
  constructor(
    private http: HttpClient,
    @Inject(PortfolioService) private portfolioService: PortfolioService

  ) {
  }
  getDataPortfolio(){
    this.portfolioService.getDataPortfolio().subscribe((data: any[]) => {
      this.data = data;
      this.items = this.data;
      console.log("the result as an array of JSON objects: ", this.data);
    })
  }
  //******* Implement your APIs ********
  getItems(): Observable<any> {
    console.log("the result as an array of JSON objects: ", this.items);
    this.getDataPortfolio();
    return  of(this.items.slice())
  }

  addItem(item): Observable<any> {
    item._id = Math.round(Math.random() * 10000000000).toString();
    this.items.unshift(item);
    return of(this.items.slice()).pipe(delay(1000));
  }
  // consume addInvestment from PortfolioService

  updateItem(id, item) {
    this.items = this.items.map(i => {
      if(i._id === id) {
        return Object.assign({}, i, item);
      }
      return i;
    })
    return of(this.items.slice()).pipe(delay(1000));
  }
  removeItem(row) {
    let i = this.items.indexOf(row);
    this.items.splice(i, 1);
    return of(this.items.slice()).pipe(delay(1000));
  }
}
