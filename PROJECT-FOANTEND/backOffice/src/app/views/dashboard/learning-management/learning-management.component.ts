import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ResourcePopupComponent } from 'app/resource-popup/resource-popup.component';
import { ResourceService } from 'app/Resource/resource.service';
import { egretAnimations } from 'app/shared/animations/egret-animations';
import { EventService } from 'app/shared/services/Event/event.service';
import { TradeService } from 'app/Trade/trade.service';

@Component({
  selector: 'app-learning-management',
  templateUrl: './learning-management.component.html',
  styleUrls: ['./learning-management.component.scss'],
  animations: egretAnimations
})
export class LearningManagementComponent implements OnInit {
  welcomeProgressChart = { /* ... existing chart config ... */ };
  tradeForm: FormGroup;
  assets = [
    { id: 1, assetName: 'Bitcoin' },
    { id: 2, assetName: 'Ethereum' },
    { id: 3, assetName: 'Litecoin' },
  ];
  studyChart = {
    series: [],
    chartOptions: {
      chart: {
        type: 'bar',
        height: 300,
        stacked: true,
        toolbar: {
          show: false,
        },
      },
      legend: {
        position: 'top',
        horizontalAlign: 'left',
        offsetX: -35,
        itemMargin: {
          horizontal: 10,
        },
        markers: {
          width: 10,
          height: 10,
          radius: 40
        }
      },
      plotOptions: {
        bar: {
          horizontal: false,
          columnWidth: '20px',
        },
      },
      dataLabels: {
        enabled: false,
      },
      colors: [],
      xaxis: {
        axisBorder: {
          show: false,
        },
        categories: [],
      },
      yaxis: {
        show: false,
      },
      grid: {
        show: false,
      },
    },
  };

  courses = [ /* ... existing courses ... */ ];
  results = [];
  loading: boolean = false;
  error: string | null = null;
  selectedUser: any = null;
  reminders = [ /* ... existing reminders ... */ ];
  resources = [];
  public events: [];
  resourceTypeIcons: { [key: string]: string } = {
    VIDEO: 'play_circle', // Icon for videos
    PDF: 'picture_as_pdf', // Icon for PDFs
    ARTICLE: 'description', // Icon for articles
    TOOL: 'build', // Icon for tools
  };
  constructor(  private dialog: MatDialog,    private tradeeService: TradeService, private fb: FormBuilder, private http: HttpClient, private eventService: EventService, private resourceService: ResourceService) {}
  openCreateResourcePopup(): void {
    const dialogRef = this.dialog.open(ResourcePopupComponent, {
      width: '500px',
      data: {},
    });
  
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        // Save the resource to the backend
        this.resourceService.createResource(result).subscribe({
          next: (response) => {
            console.log('Resource created successfully:', response);
            this.loadResources(); // Refresh the resource list
          },
          error: (err) => {
            console.error('Error creating resource:', err);
          },
        });
      }
    });
  }
  loadResources(): void {
    this.resourceService.getAllResourcess().subscribe({
      next: (resources: any[]) => {
        this.resources = resources.map(resource => ({
          id: resource.id,
          title: resource.resourceTitle,
          type: resource.resourceType,
          url: resource.url,
          icon: this.resourceTypeIcons[resource.resourceType],
        }));
      },
      error: (err) => {
        console.error('Error loading resources:', err);
      },
    });
  }
  onUserSelected(user: any): void {
    this.selectedUser = user;
    this.results = []
    this.updateChart({groupedData:[]});
    this.fetchTradesByUserId(user.id);
    this.tradeForm.get('userId')?.setValue(user.id);
    this.loadEvents();
  }
  loadEvents(): void {
    this.eventService.getAllEvents().subscribe({
      next: (events: any[]) => {
        const currentDate = new Date();
  
        this.reminders = events
          .filter(
            (event) =>
              new Date(event.eventStartDate) <= currentDate &&
              new Date(event.eventEndDate) >= currentDate
          )
          .map((event) => ({
            id: event.id,
            title: event.eventTitle,
            date: `${new Date(event.eventStartDate).toLocaleDateString()} - ${new Date(
              event.eventEndDate
            ).toLocaleDateString()}`,
            participants: event.participants,
            type: event.eventType,
          }));
      },
      error: (err) => {
        console.error('Error loading events:', err);
      },
    });
  }
  addParticipantToEvent(userId: string, eventId: string): void {
    this.eventService.addParticipant(userId, eventId).subscribe({
      next: () => {
        console.log('Participant added successfully');
        this.loadEvents(); 
      },
      error: (err) => {
        console.error('Error adding participant:', err);
        this.loadEvents(); 

      },
    });
  }
  isParticipant(event: any, userId: number): boolean {
    return event.participants.some((participant: any) => participant.id === userId);
  }
  eventTypeIcons: { [key: string]: string } = {
    TRADING_COMPETITION: 'emoji_events', 
    TRAINING_SESSION: 'school',         
    WORKSHOP: 'build',                  
    CONFERENCE: 'groups',               
  };
  groupTradesByAssetAndDate(trades: any[]): { [key: string]: { date: string, profit: number }[] } {
    return trades.reduce((acc, trade) => {
      const assetName = trade.asset.assetName;
      const tradeDate = new Date(trade.tradeDate).toLocaleDateString();
      const key = `${assetName}-${tradeDate}`;

      if (!acc[assetName]) {
        acc[assetName] = [];
      }

      const existingEntry = acc[assetName].find(entry => entry.date === tradeDate);
      if (existingEntry) {
        existingEntry.amount += trade.amount;
      } else {
        acc[assetName].push({ date: tradeDate, profit: trade.amount });
      }

      return acc;
    }, {});
  }

  updateChart(groupedData: { [key: string]: { date: string, profit: number }[] }): void {
    const series = [];
    const colors = [];
    const uniqueAssets = Object.keys(groupedData);

    uniqueAssets.forEach((asset, index) => {
      const assetData = groupedData[asset];
      const dates = assetData.map(entry => entry.date);
      const profits = assetData.map(entry => entry.profit);

      series.push({
        name: asset,
        data: profits,
        type: 'bar',
        stack: 'one',
      });

      colors.push(this.generateColor(index));
      if (index === 0) {
        this.studyChart.chartOptions.xaxis.categories = dates;
      }
    });

    this.studyChart.series = series;
    this.studyChart.chartOptions.colors = colors;
  }

  generateColor(index: number): string {
    const colors = ['#0081FF', '#E95455', '#E97D23', '#3AB54A', '#8A2BE2'];
    return colors[index % colors.length];
  }

  fetchTradesByUserId(userId: number): void {
        this.loading = true;
  
    this.tradeeService.getTradesByUserId(userId).subscribe({
      next: async (trades) => {
        const updatedResults = await Promise.all(
          trades.map(async (trade: any) => {
            const assetName = trade.asset.assetName;
            const price = await this.fetchAssetPriceForTrade(assetName);
            const completed = price > 0 ? ((trade.amount/price)-trade.profit)/trade.profit*100 : 0;
        
            return {
              name: assetName,
              color: 'primary',
              date: new Date(trade.tradeDate).toLocaleDateString(),
              completed: completed,
            };
          })
        );
  
        this.results = updatedResults;
  
        const groupedData = this.groupTradesByAssetAndDate(trades);
        this.updateChart(groupedData);
        this.loading = false;
      },
      error: (err) => {
        console.error('Error fetching trades:', err);
        this.error = 'Failed to fetch trades. Please try again later.';
        this.loading = false;
      },
    });
  }
  
  fetchAssetPriceForTrade(assetName: string): Promise<number> {
    const apiUrl = `https://api.coingecko.com/api/v3/simple/price?ids=${assetName.toLowerCase()}&vs_currencies=usd`;
  
    return new Promise((resolve) => {
      this.http.get(apiUrl).subscribe({
        next: (data: any) => {
          const price = data[assetName.toLowerCase()]?.usd || 0;
          resolve(price);
        },
        error: (err) => {
          console.error(`Error fetching price for ${assetName}:`, err);
          resolve(0); // Default to 0 in case of an error
        },
      });
    });
  }
  

  ngOnInit(): void {
    this.tradeForm = this.fb.group({
      userId: [null, Validators.required],
      asset: ['', Validators.required],
      profit: [{ value: 0, disabled: true }],
      amount: [0, [Validators.required, Validators.min(0)]],
    });
    this.loadResources();
  }
  onSubmit(): void {
    if (this.tradeForm.valid) {
      const assetName = this.tradeForm.get('asset')?.value;
      const amount = this.tradeForm.get('amount')?.value;
      const assetNamee = this.mapAssetIdToName(assetName);

      if (assetName && amount) {
        this.fetchAssetPrice(assetName, amount, (price: number) => {
          const profit = price > 0 ? (amount / price).toFixed(2) : 0;

          const trade = {
            user: {
              id: this.selectedUser?.id,
              username: this.selectedUser?.username,
              email: this.selectedUser?.email,
              accountBalance: this.selectedUser?.accountBalance,
            },
            asset: {
              id: assetName, 
              assetName: assetNamee,
              assetType: 'Crypto', 
            },
            profit: profit,
            amount: amount,
            tradeDate: new Date().toISOString(), 
          };
  
          this.tradeeService.createTrade(trade).subscribe({
            next: (response) => {
              console.log('Trade created successfully:', response);
              this.fetchTradesByUserId(this.selectedUser.id)
            },
            error: (error) => {
              console.error('Error creating trade:', error);
            },
          });
        });
      }
    }
  }
  mapAssetIdToName(assetId: number): string {
    const asset = this.assets.find(a => a.id == assetId);
    return asset ? asset.assetName : '';
  }
  fetchAssetPrice(assetId: number, amount: number, callback: (price: number) => void): void {
    const assetName = this.mapAssetIdToName(assetId);

    const apiUrl = `https://api.coingecko.com/api/v3/simple/price?ids=${assetName}&vs_currencies=usd`;

    this.http.get(apiUrl).subscribe({
      next: (data: any) => {
        const price = data[assetName.toLocaleLowerCase()]?.usd || 0;
        console.log(data)
        console.log(data[assetName.toLocaleLowerCase()])
        const profit = price > 0 ? (amount / price).toFixed(2) : 0;

        this.tradeForm.get('profit')?.setValue(profit);
        console.log('Trade Details:', this.tradeForm.value);
        callback(price);

      },
      error: (err) => {
        console.error('Error fetching asset price:', err);
        callback(0);
      },
    });
  }
}


