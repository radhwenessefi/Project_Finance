import { Component, OnInit } from '@angular/core';
import { CryptocurrencyService } from 'app/shared/services/Event/CryptocurrencyService';
import { ThemeService } from 'app/shared/services/theme.service';
import tinyColor from 'tinycolor2';

import { StrategiesService } from 'app/shared/services/Event/StrategiesService';
import { SentimentAIService } from 'app/shared/services/Event/SentimentAIService';

@Component({
  selector: 'app-cryptocurrency',
  templateUrl: './cryptocurrency.component.html',
  styleUrls: ['./cryptocurrency.component.scss'],
})
export class CryptocurrencyComponent implements OnInit {
  cryptoPrices: any;
  cryptoChart: any;
  inputText: string = '';
  sentimentScore: number | null = null;
  sentimentLabel: string = '';
  loading: boolean = false;
  // Propriétés supplémentaires pour les données affichées
  investmentAmount: number = 15000; // Exemple de montant d'investissement
  profitAmount: number = 3000; // Exemple de montant de profit
  transactionCount: number = 120; // Exemple de nombre de transactions
  btcChangePercentage: number = 0; // Variation en pourcentage de Bitcoin
  ethChangePercentage: number = 0; // Variation en pourcentage d'Ethereum
  ltcChangePercentage: number = 0; // Variation en pourcentage de Litecoin
  eurConversionRate: number = 0.94; // Exemple de taux de conversion USD → EUR
 // Entrées utilisateur

  dcaInterval: number = 7; // Intervalle DCA (jours)
  dipThreshold: number = 0.1; // Seuil pour Buy the Dip

  // Résultats des stratégies
  dcaResult: number = 0;
  buyTheDipResult: number = 0;
  hodlResult: number = 0;

  prices: number[] = []; // Données historiques des prix (simulées)
  chartOptions: any;
  showExplanation: boolean = false;

toggleExplanation(): void {
  this.showExplanation = !this.showExplanation;
}


  constructor(
    private cryptoService: CryptocurrencyService,
    private themeService: ThemeService,private strategiesService: StrategiesService,private sentimentAIService: SentimentAIService
  ) {}
 /* analyzeSentiment(): void {
    if (!this.inputText) return;

    this.sentimentScore = this.sentimentAIService.analyzeSentiment(this.inputText);
    this.sentimentLabel =
      this.sentimentScore > 0.5
        ? 'Positif'
        : this.sentimentScore < 0.5
        ? 'Négatif'
        : 'Neutre';
  }*/
  ngOnInit(): void {
    //this.loading = true;
  // this.sentimentAIService.loadModel();
    this.loading = false;
     // Simuler des données historiques pour le test
     this.prices = [50000, 48000, 49000, 47000, 46000, 45000, 47000, 44000, 43000, 45000];


     this.prices = Array.from({ length: 30 }, (_, i) => 50000 + Math.random() * 2000 - 1000);
    this.themeService.onThemeChange.subscribe((activeTheme) => {
      this.initCryptoChart(activeTheme);
    });
    this.initCryptoChart(this.themeService.activatedTheme);
    this.fetchCryptoData();
  }
   // Calcul des stratégies
   calculateStrategies(): void {
    const seuil = this.dipThreshold / 100; // Convertir le seuil en pourcentage
    if (!this.prices || this.prices.length === 0) {
      console.error('Les prix ne sont pas disponibles pour le calcul.');
      return;
    }
  
    // Résultats de Buy the Dip
    const buyTheDipResult = this.strategiesService.calculateBuyTheDip(this.prices, seuil, this.investmentAmount);
    this.buyTheDipResult = buyTheDipResult.profitLoss;
  
    // Résultats de DCA
    const dcaResult = this.strategiesService.calculateDCA(this.prices, this.investmentAmount, this.dcaInterval);
    this.dcaResult = dcaResult.profitLoss;
  
    // Résultats de HODL
    const hodlResult = this.strategiesService.calculateHODL(this.prices, this.investmentAmount);
    this.hodlResult = hodlResult.profitLoss;
  
    // Configure le graphique pour comparer les stratégies
    this.chartOptions = {
      tooltip: {
        trigger: 'axis',
        formatter: params => {
          let tooltipText = `${params[0].axisValue}<br>`;
          params.forEach(param => {
            tooltipText += `${param.seriesName}: ${param.data.toFixed(2)} USD<br>`;
          });
          return tooltipText;
        }
      },
      legend: {
        data: ['DCA', 'Buy the Dip', 'HODL'],
        top: '10'
      },
      xAxis: {
        type: 'category',
        data: this.prices.map((_, i) => `Jour ${i + 1}`)
      },
      yAxis: {
        type: 'value',
        axisLabel: {
          formatter: '${value}'
        }
      },
      series: [
        {
          name: 'DCA',
          data: dcaResult.chartData.map(data => data.value),
          type: 'line',
          smooth: true,
          areaStyle: {}
        },
        {
          name: 'Buy the Dip',
          data: buyTheDipResult.chartData.map(data => data.value),
          type: 'line',
          smooth: true,
          areaStyle: {}
        },
        {
          name: 'HODL',
          data: hodlResult.chartData.map(data => data.value),
          type: 'line',
          smooth: true,
          areaStyle: {}
        }
      ]
    };
  }
  
  

  fetchCryptoData(): void {
    this.cryptoService.getCryptoPrices().subscribe({
      next: (data) => {
        this.cryptoPrices = data;
        console.log('Crypto Prices:', this.cryptoPrices);
        this.updateChartData();
      },
      error: (err) => {
        console.error('Error fetching crypto prices:', err);
        alert('Failed to fetch crypto prices. Please try again later.');
      },
    });
  }
  
  
  initCryptoChart(theme): void {
    this.cryptoChart = {
      tooltip: {
        show: true,
        trigger: 'axis',
        backgroundColor: '#fff',
        extraCssText: 'box-shadow: 0 0 3px rgba(0, 0, 0, 0.3); color: #444',
        axisPointer: {
          type: 'line',
          animation: true,
        },
      },
      grid: {
        top: '10%',
        left: '60',
        right: '20',
        bottom: '60',
      },
      xAxis: {
        type: 'category',
        data: Array.from({ length: 30 }, (_, i) => `${i + 1}`), // Jours
        axisLine: { show: false },
        axisLabel: { show: true, margin: 30, color: '#888' },
        axisTick: { show: false },
      },
      yAxis: {
        type: 'value',
        axisLine: { show: false },
        axisLabel: { show: true, margin: 20, color: '#888' },
        axisTick: { show: false },
        splitLine: { show: true, lineStyle: { type: 'dashed' } },
      },
      series: [
        {
          data: [], // Bitcoin
          type: 'line',
          name: 'Bitcoin',
          smooth: true, // Ligne fluide
          color: tinyColor(theme.baseColor).toString(), // Couleur personnalisée
          lineStyle: { opacity: 1, width: 3 },
          itemStyle: { opacity: 0 },
          emphasis: {
            itemStyle: {
              color: tinyColor(theme.baseColor).toString(),
              borderColor: tinyColor(theme.baseColor).setAlpha(0.4).toString(),
              opacity: 1,
              borderWidth: 8,
            },
          },
        },
        {
          data: [], // Ethereum
          type: 'line',
          name: 'Ethereum',
          smooth: true,
          color: 'rgba(0, 0, 0, .3)',
          lineStyle: { opacity: 1, width: 3 },
          itemStyle: { opacity: 0 },
          emphasis: {
            itemStyle: {
              color: 'rgba(0, 0, 0, .5)',
              borderColor: 'rgba(0, 0, 0, .2)',
              opacity: 1,
              borderWidth: 8,
            },
          },
        },
      ],
    };
  }
  
  updateChartData(): void {
    if (this.cryptoPrices) {
      const bitcoinPrice = this.cryptoPrices.bitcoin.usd || 0;
      const ethereumPrice = this.cryptoPrices.ethereum.usd || 0;
  
      // Générer des variations spécifiques pour Bitcoin
      this.cryptoChart.series[0].data = Array.from({ length: 30 }, (_, i) => 
        bitcoinPrice + Math.sin(i) * 2000 + Math.random() * 1000
      );
  
      // Générer des variations spécifiques pour Ethereum
      this.cryptoChart.series[1].data = Array.from({ length: 30 }, (_, i) => 
        ethereumPrice + Math.cos(i) * 1500 + Math.random() * 500
      );
  
      console.log('Bitcoin Data:', this.cryptoChart.series[0].data);
      console.log('Ethereum Data:', this.cryptoChart.series[1].data);
  
      // Forcer la mise à jour du graphique
      this.cryptoChart = { ...this.cryptoChart };
    }
  }
  
  
  

  calculateChangePercentage(currentPrice: number, previousPrice: number): number {
    if (!currentPrice || !previousPrice) return 0;
    return ((currentPrice - previousPrice) / previousPrice) * 100;
  }
}
