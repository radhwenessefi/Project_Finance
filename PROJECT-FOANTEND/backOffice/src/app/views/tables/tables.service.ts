import { Injectable } from '@angular/core';
import { PortfolioService } from 'app/views/Portfolio-Service/portfolio.Service';
import { Inject } from '@angular/core';
import { get } from 'http';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TablesService {
  data: any;
  items: any[];
  constructor(
    private http: HttpClient,
    @Inject(PortfolioService) private portfolioService: PortfolioService
  ) { }
  getDataConf() {
    return [
    ];
  }
  getAllPortfolioInvestment(){
    this.portfolioService.getAllInvestment().subscribe((data: any[]) => {
      this.data = data;
      this.items = this.data;
      console.log("the result as an array of JSON objects: ", this.data);
    })
    
  }
  //******* Implement your APIs ********
  getItems(): Observable<any> {
    console.log("the result as an array of JSON objects: ", this.items);
    this.getAllPortfolioInvestment();
    return  of(this.items.slice())
  }


}
