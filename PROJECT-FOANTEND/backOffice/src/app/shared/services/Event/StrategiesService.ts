import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StrategiesService {

  // Stratégie DCA
  calculateDCA(prices: number[], totalInvestmentAmount: number, interval: number): { profitLoss: number, chartData: { date: string, value: number }[] } {
    let totalInvestment = 0;
    let totalCoins = 0;
    const chartData = [];
  
    for (let i = 0; i < prices.length; i += interval) {
      const investment = totalInvestmentAmount / Math.ceil(prices.length / interval);
      totalInvestment += investment;
      totalCoins += investment / prices[i];
      chartData.push({ date: `Jour ${i + 1}`, value: totalCoins * prices[i] - totalInvestment });
    }
  
    const finalValue = totalCoins * prices[prices.length - 1];
    return { profitLoss: finalValue - totalInvestment, chartData };
  }
  
  

  calculateBuyTheDip(prices: number[], threshold: number, totalInvestmentAmount: number): { profitLoss: number, chartData: { date: string, value: number }[] } {
    let totalInvestment = 0;
    let totalCoins = 0;
    let availableInvestment = totalInvestmentAmount; // Investissement total disponible
    const chartData = [];
  
    for (let i = 1; i < prices.length; i++) {
      if (prices[i] < prices[i - 1] * (1 - threshold)) { // Détecte une baisse
        const investment = availableInvestment * 0.1; // 10 % de l'investissement restant
        if (investment > 0) {
          totalInvestment += investment;
          totalCoins += investment / prices[i];
          availableInvestment -= investment;
        }
      }
  
      const finalValue = totalCoins * prices[i]; // Valeur actuelle des coins
      chartData.push({ date: `Jour ${i + 1}`, value: finalValue - totalInvestment });
    }
  
    const finalValue = totalCoins * prices[prices.length - 1];
    return { profitLoss: finalValue - totalInvestment, chartData };
  }
  
  
  
  
  

  // Stratégie HODL
  calculateHODL(prices: number[], totalInvestmentAmount: number): { profitLoss: number, chartData: { date: string, value: number }[] } {
    const initialPrice = prices[0];
    const finalPrice = prices[prices.length - 1];
    const totalCoins = totalInvestmentAmount / initialPrice;
    const profitLoss = totalCoins * finalPrice - totalInvestmentAmount;
  
    const chartData = prices.map((price, i) => ({
      date: `Jour ${i + 1}`,
      value: totalCoins * price - totalInvestmentAmount
    }));
  
    return { profitLoss, chartData };
  }
  
  
}
