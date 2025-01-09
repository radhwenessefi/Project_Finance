import { Component, OnInit, ViewChild } from '@angular/core';
import { TablesService } from '../tables.service';
import { MatPaginator as MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource as MatTableDataSource } from '@angular/material/table';
import { egretAnimations } from 'app/shared/animations/egret-animations';
import { PortfolioService } from 'app/views/Portfolio-Service/portfolio.Service';
import { Inject } from '@angular/core';
import { MatDialogRef as MatDialogRef, MatDialog as MatDialog } from '@angular/material/dialog';
import { MatSnackBar as MatSnackBar } from '@angular/material/snack-bar';
import { AppConfirmService } from 'app/shared/services/app-confirm/app-confirm.service';
import { AppLoaderService } from 'app/shared/services/app-loader/app-loader.service';
import { Subscription } from 'rxjs';
import { PredictionPopupComponent } from './prediction-popup/prediction-popup.component';
import { UpdatePopupComponent } from 'app/views/cruds/crud-ngx-table/update-popup/update-popup.component';

import { FormsModule } from '@angular/forms';
import { StrategyService } from './service/strategy.service';





@Component({
  selector: 'app-material-table',
  templateUrl: './material-table.component.html',
  styleUrls: ['./material-table.component.scss'],
  animations: egretAnimations
})
export class MaterialTableComponent implements OnInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  public getItemSub: Subscription;
  displayedColumns: string[] = ['key', 'value'];
  dataSource: any;
  data: any;
  predictionValue: any;
  amount: number = 0; // Default value for amount
  margin: number = 0;  // Default value for margin
  strategy: number = 1;
  strategyStats:any
  objectKeys = Object.keys;
  
  constructor(private tableService: TablesService,
    private dialog: MatDialog,
    private snack: MatSnackBar,
    private strategyService :StrategyService,
    private confirmService: AppConfirmService,
    private loader: AppLoaderService,
    @Inject(PortfolioService) private portfolioService: PortfolioService
    
   
  
  ) { }

  ngOnInit() {
    
    this.getItems()
    this.displayedColumns = Object.keys(this.strategyStats);

  }
  onStrategyChange() {
    this.strategyService.getStrategyStats(this.amount, this.margin, this.strategy)
      .subscribe({
        next: (response) => {
          this.strategyStats = response;  
          console.log('Strategy stats:', this.strategyStats);
        },
        error: (error) => {
          console.error('Error calling API:', error);  // Handle error
        }
      });
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }
  
  getItems() {    
    this.getItemSub = this.tableService.getItems()
      .subscribe(data => {
        this.dataSource = new MatTableDataSource(data);
      })
  }
    //consume the deletePortfolio from  service portfolioService
    closeOrder(id) {
      console.log("Close order")
      this.confirmService.confirm({message: `Delete ${id}?`})
        .subscribe(res => {
          if (res) {
            this.loader.open('Deleting Portfolio');
            this.portfolioService.closeOrder(id)
              .subscribe(data => {
                this.loader.close();
                this.snack.open('Close Order!', 'OK', { duration: 4000 })
              })
              this.ngOnInit();
          }
        })
    }
    openPopUp(data: any = {},row : any) {
      let dialogRef: MatDialogRef<any> = this.dialog.open(PredictionPopupComponent, {
        width: '720px',
        disableClose: true,
        data: {predictionValue : data, row: row}
      })
      
      
   
      dialogRef.afterClosed()
        .subscribe(res => {
          if(!res) {
            // If user press cancel
            return;
          }

        })
    }
    openPopUpUpdate(data: any = {},row : any) {
      let dialogRef: MatDialogRef<any> = this.dialog.open(UpdatePopupComponent, {
        width: '720px',
        disableClose: true,
        data: {predictionValue : data, row: row}
      })
      
      
   
      dialogRef.afterClosed()
        .subscribe(res => {
          if(!res) {
            // If user press cancel
            return;
          }

        })
    }
    
    getPrediction(row) { 
      console.log("the data is: ", row.portfolios.idPortfolio)
      let id = row.portfolios.idPortfolio;
      this.portfolioService.getPrediction(id)
        .subscribe(data => {
          this.predictionValue = data;
          this.openPopUp(this.predictionValue,row)
        })
        console.log("the result as an array of JSON objects: ", this.predictionValue);
       
    }

}
