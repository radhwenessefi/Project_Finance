import { Component, OnInit, OnDestroy, ViewChild, ViewChildren, QueryList, AfterViewInit } from '@angular/core';
import { CrudService } from '../crud.service';
import { MatDialogRef, MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AppConfirmService } from '../../../shared/services/app-confirm/app-confirm.service';
import { AppLoaderService } from '../../../shared/services/app-loader/app-loader.service';
import { NgxTablePopupComponent } from './ngx-table-popup/ngx-table-popup.component';
import { Subscription } from 'rxjs';
import { egretAnimations } from "../../../shared/animations/egret-animations";
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { PortfolioService } from 'app/views/Portfolio-Service/portfolio.Service';
import { Inject } from '@angular/core';

@Component({
  selector: 'app-crud-ngx-table',
  templateUrl: './crud-ngx-table.component.html',
  animations: egretAnimations
})
export class CrudNgxTableComponent implements OnInit, OnDestroy, AfterViewInit {
  @ViewChildren(MatPaginator) paginators: QueryList<MatPaginator>;
  public contentArray = []; // Populated dynamically
  public displayedColumns: string[] = ['ticker', 'open', 'high', 'low', 'volume'];
  public getItemSub: Subscription;
  data: any;
  portfolioID: any;

  constructor(
    private dialog: MatDialog,
    private snack: MatSnackBar,
    private crudService: CrudService,
    private confirmService: AppConfirmService,
    private loader: AppLoaderService,
    @Inject(PortfolioService) private portfolioService: PortfolioService
  ) {}

  ngOnInit() {
    console.log('ngOnInit triggered');
    this.getDataPortfolio();
  }

  ngAfterViewInit() {
    // Listen for changes in `paginators` and assign them dynamically
    this.paginators.changes.subscribe(() => {
      this.contentArray.forEach((item, index) => {
        const paginator = this.paginators.toArray()[index];
        item.dataSource.paginator = paginator;
      });
    });
  }

  ngOnDestroy() {
    if (this.getItemSub) {
      this.getItemSub.unsubscribe();
    }
  }

  getDataPortfolio() {
    this.portfolioService.getDataPortfolio().subscribe((data: any[]) => {
      this.data = data;
      this.transformData();
      console.log("Data loaded into 'this.contentArray':", this.contentArray);
    });
  }

  transformData() {
    // Convert `this.data` into a format suitable for rendering with clusters
    this.contentArray = Object.entries(this.data || {}).map(([key, value]) => ({
      cluster: key, // Cluster identifier
      dataSource: new MatTableDataSource(value as any[]), // Create MatTableDataSource for each cluster
    }));
  }
  createPortfolio() {
    setTimeout(() => {
      this.loader.open('Creating Portfolio');
      setTimeout(() => {
        this.loader.close();
        this.snack.open('Portfolio Created!', 'OK', { duration: 4000 });

        this.portfolioService.createPortfolio().subscribe((data: any[]) => {
          console.log('Portfolio created:', data);
        }, error => {
          console.log('Error creating portfolio:', error);
        });
      }, 5000);
    }, 5000);
  }
  openPopUp(data: any = {}) {
    const dialogRef: MatDialogRef<any> = this.dialog.open(NgxTablePopupComponent, {
      width: '720px',
      disableClose: true,
      data: { payload: data, portfolioID: data }
    });
    this.portfolioID = data;
    console.log('The ID of the portfolio:', this.portfolioID);

    dialogRef.afterClosed().subscribe(res => {
      if (!res) return;

      this.loader.open('Adding new Customer');
      this.crudService.addItem(res).subscribe(
        responseData => {
          console.log('New customer added:', responseData);
          this.loader.close();
          this.snack.open('New Order Added!', 'OK', { duration: 4000 });
          this.getDataPortfolio(); // Reload data
        },
        error => {
          console.log('Error adding item:', error);
          this.loader.close();
        }
      );
    });
  }
}
