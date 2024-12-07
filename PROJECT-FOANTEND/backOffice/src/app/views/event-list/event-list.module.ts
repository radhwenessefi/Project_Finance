import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EventListComponent } from './event-list.component';
import { RouterModule, Routes } from '@angular/router';

// Define the route for this module
const routes: Routes = [
  { path: '', component: EventListComponent }, // Default route for EventListModule
];

@NgModule({
  declarations: [EventListComponent], // Declare your component here
  imports: [
    CommonModule,
    RouterModule.forChild(routes), // Import RouterModule with routes
  ],
})
export class EventListModule {}
