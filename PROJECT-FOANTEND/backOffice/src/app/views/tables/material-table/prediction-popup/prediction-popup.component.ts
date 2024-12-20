import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UntypedFormBuilder } from '@angular/forms';
import { PortfolioService } from 'app/views/Portfolio-Service/portfolio.Service';
import { HttpClient } from '@angular/common/http';
import { MatDialogModule } from '@angular/material/dialog';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-prediction-popup',
  standalone: true,
  imports: [MatDialogModule, ReactiveFormsModule, CommonModule],
  templateUrl: './prediction-popup.component.html',
  styleUrls: ['./prediction-popup.component.scss'],
  providers: [PortfolioService],
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
  idpro: any
  myPort : any

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<PredictionPopupComponent>,
    private fb: UntypedFormBuilder,
    private http: HttpClient,
    private portfolioService: PortfolioService
  ) {}

  ngOnInit() {
    if (!this.data || !this.data.row) {
      console.error('Data is missing or invalid');
      return;
    }
  
    this.prectionValue = this.data.predictionValue;
    this.allInvestment = this.data.row;
    this.idpro = this.data.row.idPortfolioInvestement;
  
    console.log('Prediction Value:', this.prectionValue);
    console.log('Investment Data:', this.allInvestment);
  
    // Fetch portfolio data and then perform calculations
    this.getDataPortfolio();
  }
  
  getDataPortfolio() {
    this.portfolioService.getDataPortfoliobyID(this.idpro).subscribe(
      (data: any) => {
        this.myPort = data;
        console.log('Portfolio Data:', this.myPort);
  
        if (!this.myPort?.Open) {
          console.error('Portfolio Open price is missing or invalid');
          return;
        }
  
        // Perform calculations after portfolio data is fetched
        this.investmentAmount(this.prectionValue);
        this.returnonInvestment(this.prectionValue);
        this.stopLossCalculation();
        this.takeProfitCalculation();
        this.riskPercentageofInvestment();
        this.potentialProfit(this.prectionValue);
  
        // Log results
        console.log('Investment Amount:', this.investmentAmountresult);
        console.log('ROI:', this.ROI);
        console.log('Stop Loss:', this.stopLoss);
        console.log('Take Profit:', this.takeProfit);
        console.log('Risk Percentage:', this.riskPercentage);
        console.log('Potential Profit:', this.resultpotentialProfit);
      },
      (error) => {
        console.error('Failed to fetch portfolio data:', error);
      }
    );
  }
  
  potentialProfit(prectionValue: any) {
    const amount = this.allInvestment.amount ?? 0;
    const takeProfit = this.takeProfit ?? 0;
    const stopLoss = this.stopLoss ?? 0;
    const orderType = this.allInvestment.orderType;
  
    if (orderType === 'buy') {
      this.resultpotentialProfit = (takeProfit - prectionValue) * amount;
    } else {
      this.resultpotentialProfit = (prectionValue - stopLoss) * amount;
    }
  
    return this.resultpotentialProfit;
  }
  
  investmentAmount(prectionValue: any) {
    this.investmentAmountresult = prectionValue * 50; // Example calculation
    return this.investmentAmountresult;
  }
  
  returnonInvestment(prectionValue: any) {
    const investmentAmount = this.allInvestment.amount ?? 0;
    if (investmentAmount === 0) {
      console.error('Investment amount is missing or invalid');
      return 0;
    }
  
    this.ROI = ((prectionValue - investmentAmount) / investmentAmount) * 100;
    return this.ROI;
  }
  
  stopLossCalculation() {
    const currentPrice = this.myPort?.Open ?? 0;
    if (currentPrice === 0) {
      console.error('Stop Loss Calculation: Current Price is missing or invalid');
      return 0;
    }
  
    this.stopLoss = currentPrice - currentPrice * 0.05; // 5% loss
    return this.stopLoss;
  }
  
  takeProfitCalculation() {
    const currentPrice = this.myPort?.Open ?? 0;
    if (currentPrice === 0) {
      console.error('Take Profit Calculation: Current Price is missing or invalid');
      return 0;
    }
  
    this.takeProfit = currentPrice + currentPrice * 0.1; // 10% profit
    return this.takeProfit;
  }
  
  riskPercentageofInvestment() {
    const currentPrice = this.myPort?.Open ?? 0;
    const stopLoss = this.stopLoss ?? 0;
  
    if (currentPrice === 0 || stopLoss === 0) {
      console.error('Risk Percentage Calculation: Current Price or Stop Loss is missing');
      return 0;
    }
  
    this.riskPercentage = ((currentPrice - stopLoss) / currentPrice) * 100;
    return this.riskPercentage;
  }
}  