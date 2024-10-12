import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef as MatDialogRef, MAT_DIALOG_DATA as MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UntypedFormBuilder, Validators, UntypedFormGroup } from '@angular/forms';
import { PortfolioService } from 'app/views/Portfolio-Service/portfolio.Service';
import { get } from 'http';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-prediction-popup',
  standalone: true,
  imports: [],
  templateUrl: './prediction-popup.component.html',
  styleUrls: ['./prediction-popup.component.scss']
 
})
export class PredictionPopupComponent implements OnInit {
  prectionValue: any;
  allInvestment: any;
  ROI: any;
  stopLoss: any;
  takeProfit: any;
  riskPercentage: any;
  resultpotentialProfit: any;
  investmentAmountresult: any;
  
  
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<PredictionPopupComponent>,
    private fb: UntypedFormBuilder,
    private http: HttpClient,
    @Inject(PortfolioService) private portfolioService: PortfolioService
  ) { }
  ngOnInit() {
    
    this.prectionValue = this.data.predictionValue;
     this.allInvestment = this.data.row;
    console.log("the data iiiiiiiiiiid: ", this.prectionValue)
    console.log("the data Investment is : ", this.allInvestment)
    this.investmentAmount(this.prectionValue);
    this.returnonInvestment(this.prectionValue, this.allInvestment);
    this.stopLossCalculation(this.allInvestment);
    this.takeProfitCalculation(this.allInvestment);
    this.riskPercentageofInvestment(this.allInvestment);
    this.potentialProfit(this.prectionValue, this.allInvestment);
    console.log("the investment amount is: ", this.investmentAmount(this.prectionValue));
    console.log("the return on investment is: ", this.returnonInvestment(this.prectionValue, this.allInvestment));
    console.log("the stop loss is: ", this.stopLossCalculation(this.allInvestment));
    console.log("the take profit is: ", this.takeProfitCalculation(this.allInvestment));
    console.log("the risk percentage of investment is: ", this.riskPercentageofInvestment(this.allInvestment));
    console.log("the potential profit is: ", this.potentialProfit(this.prectionValue, this.allInvestment));

  }
  potentialProfit(prectionValue, allInvestment) {
    
    let amount = allInvestment.amount;
    let takeProfit = allInvestment.takeProfit;
    let stopLoss = allInvestment.stopLoss;
    let orderType = allInvestment.orderType;
    if (orderType == 'buy') {
      this.resultpotentialProfit = (prectionValue - takeProfit) * amount;
    } else {
      this.resultpotentialProfit = (stopLoss - prectionValue) * amount;
    }
    return this.resultpotentialProfit;

  }
  investmentAmount(prectionValue) {
   
    //Calculate the amount of money you will invest based on the predicted price and the number of shares you want to buy
     this.investmentAmountresult = prectionValue * 50;
    return this.investmentAmountresult;
   }
   //Calculate the return on your investment based on the predicted price and the initial investment amount
   returnonInvestment(prectionValue,allInvestment){
    let InvestmentAmount = allInvestment.amount;
    
    this.ROI = ((prectionValue - InvestmentAmount) / InvestmentAmount)*100;
    return this.ROI;
   }
   //Determine the stop-loss price based on your risk tolerance. For example, if you're willing to tolerate a 5% loss:
  stopLossCalculation(allInvestment){
  
    let currentPrice = allInvestment.portfolios.Open;
    this.stopLoss = currentPrice - (currentPrice * 0.05);
    return this.stopLoss;
  }
  //Set a take-profit price based on your desired profit target. For instance, if you aim for a 10% profit:
  takeProfitCalculation(allInvestment){
  
    let currentPrice = allInvestment.portfolios.Open;
    this.takeProfit = currentPrice + (currentPrice * 0.1);
    return this.takeProfit;
  }
 //Calculate the percentage of your investment that you are willing to risk based on your stop-loss strategy
  riskPercentageofInvestment(allInvestment){
    
    let currentPrice = allInvestment.portfolios.Open;
    let stopLoss = this.allInvestment.stopLoss;
    this.riskPercentage = ((stopLoss - currentPrice) / currentPrice)*100;
    return this.riskPercentage;
  }
}
