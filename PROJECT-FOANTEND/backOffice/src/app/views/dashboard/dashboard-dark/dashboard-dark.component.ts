import { Component, OnInit, OnDestroy } from '@angular/core';
import { egretAnimations } from 'app/shared/animations/egret-animations';
import { LayoutService } from 'app/shared/services/layout.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ForexService } from './forex/forex.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-analytics-alt',
  templateUrl: './dashboard-dark.component.html',
  styleUrls: ['./dashboard-dark.component.scss'],
  animations: egretAnimations
})
export class DashboardDarkComponent implements OnInit {
  trafficchartbar: any;
  monthlyTrafficChartBar: any;
  dailyBandwithUsage: any;
  trafficGrowthChart: any;
  range = '1';
  start = "2023-01-09";
  end = "2023-01-30";
  daterange: any;
  countryTrafficStats = [
    {
      country: "US",
      visitor: 14040,
      pageView: 10000,
      download: 1000,
      bounceRate: 30,
      flag: "flag-icon-us"
    },
    // ... (reste des pays)
  ];

  newforexcolumns: string[] = ['v', 'vw', 'o', 'c', 'h'];
  forexdata: any[];

  constructor(
    private layout: LayoutService,
    private snack: MatSnackBar,
    private forexService: ForexService // Utilisation du service Forex
  ) {}

  ngOnInit() {
    this.dailyBandwithUsage = { /*... (chart configuration remains the same)*/ };
    this.trafficGrowthChart = { /*... (chart configuration remains the same)*/ };
    this.getForexDetails('EURUSD', this.range, this.start, this.end); // Appel à ForexService
  }

  createtrafficchart() {
    if (this.forexdata) {
      const volumes = this.forexdata.map(data => data["v"]);
      const xAxisData = this.forexdata.map((_, index) => (index + 1).toString());

      this.trafficchartbar = {
        legend: { show: false },
        grid: { left: "8px", right: "8px", bottom: "0", top: "0", containLabel: true },
        tooltip: { show: true, backgroundColor: "rgba(0, 0, 0, .8)" },
        xAxis: [
          {
            type: "category",
            data: xAxisData,
            axisTick: { show: true },
            splitLine: { show: true },
            axisLine: { show: true },
            axisLabel: { color: "#fff" }
          }
        ],
        yAxis: [
          {
            type: "value",
            axisLabel: { show: false, formatter: "${value}" },
            min: 150000,
            max: 250000,
             
            axisTick: { show: false },
            axisLine: { show: false },
            splitLine: { show: false }
          }
        ],
        series: [
          {
            name: "Volume",
            data: volumes,
            label: { show: false, color: "#0168c1" },
            type: "bar",
            barWidth: "8",
            color: "#f6be1a",
            smooth: true,
            itemStyle: { barBorderRadius: 10 }
          }
        ]
      };
    }
  }

  createopenclosedchart() {
    const openPrices = this.forexdata.map(data => data["h"]);
    const closePrices = this.forexdata.map(data => data["o"]);
    const xAxisData = this.forexdata.map((_, index) => (index + 1).toString());

    this.monthlyTrafficChartBar = {
      tooltip: { trigger: "axis", axisPointer: { animation: true } },
      grid: { left: "0", top: "0%", right: "0", bottom: "0%" },
      xAxis: {
        type: "category",
        boundaryGap: false,
        data: xAxisData,
        axisLabel: { show: true },
        axisLine: { lineStyle: { show: false } },
        axisTick: { show: false },
        splitLine: { show: false }
      },
      yAxis: {
        type: "value",
        min: 1,
        max: 1.2,
         
        axisLabel: { show: true },
        axisLine: { show: false },
        axisTick: { show: false },
        splitLine: { show: false }
      },
      series: [
        {
          name: "highest Price",
          type: "line",
          smooth: true,
          data: openPrices,
          symbolSize: 8,
          showSymbol: false,
          lineStyle: { opacity: 0.8, width: 2 },
          itemStyle: { borderColor: "#f6be1a" },
          areaStyle: { color: "#f6be1a", opacity: 0.5 }
        },
        {
          name: "open Price",
          type: "line",
          smooth: true,
          data: closePrices,
          symbolSize: 8,
          showSymbol: false,
          lineStyle: { opacity: 0.8, width: 2 },
          itemStyle: { borderColor: "#e91f63" },
          areaStyle: { color: "#e91f63", opacity: 0.5 }
        }
      ]
    };
  }

  applyFilters(range: string, start: string, end: string) {
    if (range && start && end) {
      this.range = range;
      this.start = start;
      this.end = end;
      const startDate = new Date(start);
      const endDate = new Date(end);
      const timeDifference = endDate.getTime() - startDate.getTime();
      this.daterange = timeDifference / (1000 * 3600 * 24);

      this.getForexDetails('EURUSD', this.range, this.start, this.end); // Appel à ForexService
    } else {
      console.log('Please fill in all fields.');
    }
  }

  getForexDetails(pair: string, range: string, start: string, end: string): void {
    this.forexService.getForexData(pair, range, start, end).subscribe({
      next: (data) => {
        this.forexdata = data.results;
        console.log('Forex data:', this.forexdata);
        this.createtrafficchart();
        this.createopenclosedchart();
      },
      error: (err) => {
        console.error('Error fetching forex data:', err);
      }
    });
  }

  ngOnDestroy() {
    setTimeout(() => {});
  }
}
