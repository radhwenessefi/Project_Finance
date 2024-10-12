import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { ConfigService } from './Config.service';


@Injectable({


    providedIn: 'root'

  })
  export class PortfolioService {




    constructor(private http:HttpClient,private config:ConfigService) { }

    url=this.config.url

    getDataPortfolio(){
        return this.http.get(this.url+"/portfolio/get-all-portfolios")
      }

    createPortfolio(){
        return this.http.post(this.url+"/portfolio/add-portfolio-fromAPI",null)
      }
      deletePortfolio(id){
        return this.http.delete(this.url+"/portfolio/remove-portfolio/"+id)
      }
      getAllInvestment(){
        return this.http.get(this.url+"/ProInvestment/get-all-portfolioInvesment")
      }
      addInvestment(data, userId, portfolioId){
        return this.http.post(this.url+"/ProInvestment/add-portfolio-Investment/" + userId + "/" + portfolioId, data);
    }
    closeOrder(id){ 
        return this.http.delete(this.url+"/ProInvestment/closeorder/"+id)
    }
    getPrediction(id){
        return this.http.post(this.url+"/portfolio/get-prediction-portfolios/"+id,null)
    }
    updateOrder(data){
      return this.http.put(this.url+"/ProInvestment/add-portfolio-Investment",  data);
    }
  }
