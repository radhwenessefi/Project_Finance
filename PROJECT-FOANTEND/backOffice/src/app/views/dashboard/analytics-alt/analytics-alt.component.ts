import { Component, OnInit, OnDestroy } from '@angular/core';
import { egretAnimations } from 'app/shared/animations/egret-animations';
import { LayoutService } from 'app/shared/services/layout.service';
import { MatSnackBar as MatSnackBar } from '@angular/material/snack-bar';
import { CryptoService } from './cryptoservice/crypto.service';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-analytics-alt',
  templateUrl: './analytics-alt.component.html',
  styleUrls: ['./analytics-alt.component.scss'],
  animations: egretAnimations
})
export class AnalyticsAltComponent implements OnInit {
  trafficchartbar: any;
  monthlyTrafficChartBar: any;
  dailyBandwithUsage: any;
  trafficGrowthChart: any;
  range='1'
  start="2023-01-09"
  end="2023-01-30"
  daterange
  countryTrafficStats = [
    {
      country: "US",
      visitor: 14040,
      pageView: 10000,
      download: 1000,
      bounceRate: 30,
      flag: "flag-icon-us"
    },
    {
        country: "India",
        visitor: 12500,
        pageView: 10000,
        download: 1000,
        bounceRate: 45,
        flag: "flag-icon-in"
    },
    {
        country: "UK",
        visitor: 11000,
        pageView: 10000,
        download: 1000,
        bounceRate: 50,
        flag: "flag-icon-gb"
    },
    {
        country: "Brazil",
        visitor: 4000,
        pageView: 10000,
        download: 1000,
        bounceRate: 30,
        flag: "flag-icon-br"
    },
    {
        country: "Spain",
        visitor: 4000,
        pageView: 10000,
        download: 1000,
        bounceRate: 45,
        flag: "flag-icon-es"
    },
    {
        country: "Mexico",
        visitor: 4000,
        pageView: 10000,
        download: 1000,
        bounceRate: 70,
        flag: "flag-icon-mx"
    },
    {
        country: "Russia",
        visitor: 4000,
        pageView: 10000,
        download: 1000,
        bounceRate: 40,
        flag: "flag-icon-ru"
    }
  ];

  newcryptocolumns: string[] = ['v', 'vw', 'o', 'c', 'h'];
  cryptodata: []
  
  constructor(
    private layout: LayoutService,
    private snack: MatSnackBar,
    private cryptoService:CryptoService
  ) {
    
  }

  ngOnInit() {
    setTimeout(() => {
      // this.layout.publishLayoutChange({sidebarColor: 'dark-blue', topbarColor: 'dark-blue', footerColor: 'dark-blue', matTheme: "egret-navy-dark"});
      // this.snack.open('Layout option changed to {sidebarColor: "dark-blue", topbarColor: "dark-blue", matTheme: "egret-navy-dark"};', 'OK', {duration: 6000})
    });
    
    

    this.dailyBandwithUsage = {
      grid: {
        left: "3%",
        right: "4%",
        bottom: "3%",
        containLabel: true
      },
      color: ["#fcc02e", "#e91f63", "#f44336"],
      tooltip: {
        show: false,
        trigger: "item",
        formatter: "{a} <br/>{b}: {c} ({d}%)"
      },
      xAxis: [
        {
          axisLine: {
            show: false
          },
          splitLine: {
            show: false
          }
        }
      ],
      yAxis: [
        {
          axisLine: {
            show: false
          },
          splitLine: {
            show: false
          }
        }
      ],

      series: [
        {
          name: "Sessions",
          type: "pie",
          radius: ["50%", "85%"],
          center: ["50%", "50%"],
          avoidLabelOverlap: false,
          hoverOffset: 5,
          stillShowZeroSum: false,
          label: {
            normal: {
              show: false,
              position: "center",
              textStyle: {
                fontSize: "13",
                fontWeight: "normal"
              },
              formatter: "{a}"
            },
            emphasis: {
              show: true,
              textStyle: {
                fontSize: "15",
                fontWeight: "normal",
                color: "white"
              },
              formatter: "{b} \n{c} ({d}%)"
            }
          },
          labelLine: {
            normal: {
              show: false
            }
          },
          data: [
            {
              value: 335,
              name: "Direct"
            },
            {
              value: 310,
              name: "Search Eng."
            },
            { value: 148, name: "Social" }
          ],
          itemStyle: {
            emphasis: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: "rgba(0, 0, 0, 0.5)"
            }
          }
        }
      ]
    };

    this.trafficGrowthChart = {
      tooltip: {
        trigger: "axis",

        axisPointer: {
          animation: true
        }
      },
      grid: {
        left: "0",
        top: "0",
        right: "0",
        bottom: "0"
      },
      xAxis: {
        type: "category",
        boundaryGap: false,
        data: [
          "0",
          "1",
          "2",
          "3",
          "4",
          
        ],
        axisLabel: {
          show: false
        },
        axisLine: {
          lineStyle: {
            show: false
          }
        },
        axisTick: {
          show: false
        },
        splitLine: {
          show: false
        }
      },
      yAxis: {
        type: "value",
        min: 0,
        max: 200,
        interval: 50,
        axisLabel: {
          show: false
        },
        axisLine: {
          show: false
        },
        axisTick: {
          show: false
        },
        splitLine: {
          show: false
        }
      },
      series: [
        {
          name: "Visit",
          type: "line",
          smooth: false,
          data: [0, 40, 140, 90, 160],
          symbolSize: 8,
          showSymbol: false,
          lineStyle: {
            opacity: 0,
            width: 0
          },
          itemStyle: {
            borderColor: "#fcc02e"
          },
          areaStyle: {
            color: '#f44336',
            opacity: 1
          }
        }
      ]
    };

    this.getCryptoDetails('BTC',this.range,this.start,this.end)
  
  
  
  }
  createtrafficchart(){
    if(this.cryptodata) {
    const volumes = this.cryptodata.map(data => data["v"]);

    const xAxisData = this.cryptodata.map((_, index) => (index + 1).toString());

    this.trafficchartbar = {
  legend: {
    show: false
  },
  grid: {
    left: "8px",
    right: "8px",
    bottom: "0",
    top: "0",
    containLabel: true
  },
  tooltip: {
    show: true,
    backgroundColor: "rgba(0, 0, 0, .8)"
  },
  xAxis: [
    {
      type: "category",
      data: xAxisData, // Use the dynamically generated X-axis data
      axisTick: {
        show: false
      },
      splitLine: {
        show: false
      },
      axisLine: {
        show: false
      },
      axisLabel: {
        color: "#fff"
      }
    }
  ],
  yAxis: [
    {
      type: "value",
      axisLabel: {
        show: false,
        formatter: "${value}"
      },
      min: 0,
      max: 100000,
      interval: 25000,
      axisTick: {
        show: false
      },
      axisLine: {
        show: false
      },
      splitLine: {
        show: false,
        interval: "auto"
      }
    }
  ],

  series: [
    {
      name: "Volume",
      data: volumes, // Set the extracted volume data here
      label: { show: false, color: "#0168c1" },
      type: "bar",
      barWidth: "8",
      color: "#f6be1a",
      smooth: true,
      itemStyle: {
        barBorderRadius: 10
      }
    }
  ]
};
}}

  createopenclosedchart(){

    // Extract the open and close prices
  const openPrices = this.cryptodata.map(data => data["h"]);
  const closePrices = this.cryptodata.map(data => data["o"]);

  // Generate the X-axis labels dynamically (e.g., ["1", "2", "3", ..., "n"])
  const xAxisData = this.cryptodata.map((_, index) => (index + 1).toString());

  // Define the chart configuration
  this.monthlyTrafficChartBar = {
    tooltip: {
      trigger: "axis",
      axisPointer: {
        animation: true
      }
    },
    grid: {
      left: "0",
      top: "0%",    // Reduced top padding for more compactness
      right: "0",
      bottom: "0%", // Reduced bottom padding for more compactness
    },
    xAxis: {
      type: "category",
      boundaryGap: false,
      data: xAxisData, // Use the dynamically generated X-axis data
      axisLabel: {
        show: true
      },
      axisLine: {
        lineStyle: {
          show: false
        }
      },
      axisTick: {
        show: false
      },
      splitLine: {
        show: false
      }
    },
    yAxis: {
      type: "value",
      min: 15000,
      max: 30000, // Adjust based on your price data range
      interval: 5000,
      axisLabel: {
        show: true
      },
      axisLine: {
        show: false
      },
      axisTick: {
        show: false
      },
      splitLine: {
        show: false
      }
    },
    series: [
      {
        name: "highest Price",
        type: "line",
        smooth: true,
        data: openPrices, // Use the open prices
        symbolSize: 8,
        showSymbol: false,
        lineStyle: {
          opacity: 0.8,
          width: 2
        },
        itemStyle: {
          borderColor: "#f6be1a"
        },
        areaStyle: {
          color: "#f6be1a",
          opacity: 0.5
        }
      },
      {
        name: "open Price",
        type: "line",
        smooth: true,
        data: closePrices, // Use the close prices
        symbolSize: 8,
        showSymbol: false,
        lineStyle: {
          opacity: 0.8,
          width: 2
        },
        itemStyle: {
          borderColor: "#e91f63"
        },
        areaStyle: {
          color: "#e91f63",
          opacity: 0.5
        }
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

    // Calculate the difference in time (in milliseconds)
    const timeDifference = endDate.getTime() - startDate.getTime();

    // Convert the time difference to days (milliseconds to days)
       this.daterange = timeDifference / (1000 * 3600 * 24);
  
      this.getCryptoDetails('BTC', this.range, this.start, this.end);
    } else {
      console.log('Please fill in all fields.');
    }
  }
  


  getCryptoDetails(symbol: string,range:string,start:string,end:string): void {
    this.cryptoService.getCryptoData(symbol,range,start,end).subscribe({
      next: (data) => {
        this.cryptodata = data.results;
        console.log('Crypto data:', this.cryptodata);
        this.createtrafficchart()
        this.createopenclosedchart()
      },
      error: (err) => {
        console.error('Error fetching crypto data:', err);
      },
    });
  }

  ngOnDestroy() {
    setTimeout(() => {
      
    });
  }
}
