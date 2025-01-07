import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule as MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule as MatChipsModule } from '@angular/material/chips';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule as MatMenuModule } from '@angular/material/menu';
import { MatProgressBarModule as MatProgressBarModule } from '@angular/material/progress-bar';
import { MatTableModule as MatTableModule } from '@angular/material/table';
import { MatTabsModule as MatTabsModule } from '@angular/material/tabs';
import { RouterModule } from '@angular/router';
import { NgChartsModule } from 'ng2-charts';
import { NgxEchartsModule } from 'ngx-echarts';
import * as echarts from 'echarts';

import { NgApexchartsModule } from 'ng-apexcharts';
import { SharedPipesModule } from '../../shared/pipes/shared-pipes.module';

import { DashboardRoutes } from './dashboard.routing';
import { AnalyticsComponent } from './analytics/analytics.component';
import { DashboardDarkComponent } from './dashboard-dark/dashboard-dark.component';
import { DefaultDashboardComponent } from './default-dashboard/default-dashboard.component';
import { LearningManagementComponent } from './learning-management/learning-management.component';
import { AnalyticsAltComponent } from './analytics-alt/analytics-alt.component';
import { AppUsersComponent } from '../others/app-users/app-users.component';
import { CryptocurrencyComponent } from './cryptocurrency/cryptocurrency.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatSelect } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports: [
    CommonModule,
    MatIconModule,
    MatCardModule,
    MatMenuModule,
    MatProgressBarModule,
    MatExpansionModule,
    MatButtonModule,
    MatChipsModule,
    MatListModule,
    MatTabsModule,
    MatTableModule,
    MatGridListModule,
    NgChartsModule,
    MatFormField,
    MatLabel,
    MatSelect,
    FormsModule,
    ReactiveFormsModule,
    MatInputModule,
    NgxEchartsModule.forRoot({
      echarts
    }),
    NgApexchartsModule,
    SharedPipesModule,
    HttpClientModule,
    RouterModule.forChild(DashboardRoutes),
    AppUsersComponent
  ],
  declarations: [
    AnalyticsComponent,
    DashboardDarkComponent,
    DefaultDashboardComponent,
    LearningManagementComponent,
    AnalyticsAltComponent,
    CryptocurrencyComponent],
  exports: []
})
export class DashboardModule {

}