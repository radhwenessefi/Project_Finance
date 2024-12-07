import { Component, OnInit, ViewChild, TemplateRef } from '@angular/core';
import { CalendarEvent, CalendarEventAction, CalendarEventTimesChangedEvent } from 'angular-calendar';
import { Subject } from 'rxjs';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { isSameDay, isSameMonth } from 'date-fns';
import { egretAnimations } from '../../shared/animations/egret-animations';
import { EgretCalendarEvent } from '../../shared/models/event.model';
import { AppCalendarService } from './app-calendar.service';
import { CalendarFormDialogComponent } from './calendar-form-dialog/calendar-form-dialog.component';
import { AppConfirmService } from '../../shared/services/app-confirm/app-confirm.service';
import { EventService } from '../../services/event.service';  // Import EventService
import { Router } from '@angular/router';

@Component({
  selector: 'app-calendar',
  templateUrl: './app-calendar.component.html',
  styleUrls: ['./app-calendar.component.css'],
  animations: egretAnimations,
})
export class AppCalendarComponent implements OnInit {
  public view = 'month';
  public viewDate = new Date();
  private dialogRef: MatDialogRef<CalendarFormDialogComponent>;
  public activeDayIsOpen: boolean = true;
  public refresh: Subject<any> = new Subject();
  public events: CalendarEvent[] = [];
  private actions: CalendarEventAction[];

  constructor(
    private eventService: EventService, // Inject EventService
    private router: Router,
    public dialog: MatDialog,  // Inject MatDialog
    private confirmService: AppConfirmService,  // Inject AppConfirmService
  ) {}

  ngOnInit() {
    this.loadEvents();
  }

  private initEvents(events): EgretCalendarEvent[] {
    return events.map((event) => {
      event.actions = this.actions;
      return new EgretCalendarEvent(event);
    });
  }

  public loadEvents() {
    this.eventService.getAllEvents().subscribe(
      (events: any[]) => {
        this.events = events.map(event => ({
          title: event.eventTitle,  // Display the event title
          start: new Date(event.eventDate),  // Assuming your API provides a startDate
          type: event.eventType, // Assuming your API provides an endDate
          // Add other necessary event properties here
        }));
      },
      (error) => {
        console.error('Error fetching events:', error);  // Handle errors
      }
    );
  }


  public addEvent() {
    this.dialogRef = this.dialog.open(CalendarFormDialogComponent, {
      panelClass: 'calendar-form-dialog',
      data: {
        action: 'add',
        date: new Date(),
      },
      width: '450px',
    });
    this.dialogRef.afterClosed().subscribe((res) => {
      if (!res) {
        return;
      }
      let dialogAction = res.action;
      let responseEvent = res.event;
      this.eventService.createEvent(responseEvent).subscribe((events) => {
        this.events = this.initEvents(events);
        this.refresh.next(true);
      });
    });
  }

  public removeEvent(event) {
    this.confirmService
      .confirm({
        title: 'Delete Event?',
      })
      .subscribe((res) => {
        if (!res) {
          return;
        }

        this.eventService.deleteEvent(event.id).subscribe((events) => {  // Update to eventService
          this.events = this.initEvents(events);
          this.refresh.next(1);
        });
      });
  }


}
