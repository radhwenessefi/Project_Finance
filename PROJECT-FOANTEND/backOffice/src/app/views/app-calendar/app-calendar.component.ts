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
import { EventService } from '../../shared/services/Event/event.service';

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
  public events: EgretCalendarEvent[];
  private actions: CalendarEventAction[];

  constructor(
    public dialog: MatDialog,
    private calendarService: AppCalendarService,
    private confirmService: AppConfirmService,
    private eventService: EventService
  ) {
    this.actions = [
      {
        label: '<i class="material-icons icon-sm">edit</i>',
        onClick: ({ event }: { event: CalendarEvent }): void => {
          this.handleEvent('edit', event);
        },
      },
      {
        label: '<i class="material-icons icon-sm">close</i>',
        onClick: ({ event }: { event: CalendarEvent }): void => {
          this.removeEvent(event);
        },
      },
    ];
  }

  ngOnInit() {
    this.loadEvents();
  }
  private getColorForEventType(eventType: string): { primary: string; secondary: string } {
    const eventTypeColors: { [key: string]: { primary: string; secondary: string } } = {
      TRADING_COMPETITION: { primary: '#1e90ff', secondary: '#D1E8FF' },
      WORKSHOP: { primary: '#ff4081', secondary: '#FFE5EC' },
      CONFERENCE: { primary: '#4caf50', secondary: '#C8E6C9' },
      TRAINING_SESSION: { primary: '#ff9800', secondary: '#FFECB3' },
    };
    return eventTypeColors[eventType] || { primary: '#607d8b', secondary: '#CFD8DC' }; // Default color
  }
  
  private initEvents(events): CalendarEvent[] {
    return events.map((event) => {
      const duration = this.getEventDuration(event.eventStartDate, event.eventEndDate);
      return {
        _id: event.id,
        id: event.id, // Backend event ID
        start: new Date(event.eventStartDate), // Map start date
        end: new Date(event.eventEndDate), // Map end date
        title: `${event.eventTitle} (${duration})`, // Include duration in the title
        color: this.getColorForEventType(event.eventType), // Assign colors based on eventType
        actions: this.actions, // Add event actions if available
        draggable: true, // Allow dragging
        resizable: {
          beforeStart: true,
          afterEnd: true,
        },
        meta: {
          eventType: event.eventType,
          notes: event.description,
          maxParticipants: event.maxParticipants,
          prizePool: event.prizePool,
        },
      };
    });
  }
  
  private getEventDuration(start: Date, end: Date): string {
    const startDate = new Date(start);
    const endDate = new Date(end);
    const diffInMs = endDate.getTime() - startDate.getTime();
    const diffInDays = Math.ceil(diffInMs / (1000 * 60 * 60 * 24)); // Convert milliseconds to days
  
    if (diffInDays <= 7) {
      return `${diffInDays+1} day${diffInDays > 1 ? 's' : ''}`;
    } else if (diffInDays <= 30) {
      const weeks = Math.ceil(diffInDays / 7);
      return `${weeks} week${weeks > 1 ? 's' : ''}`;
    } else {
      const months = Math.ceil(diffInDays / 30);
      return `${months} month${months > 1 ? 's' : ''}`;
    }
  }
    

  public loadEvents() {
    this.eventService.getAllEvents().subscribe((events: CalendarEvent[]) => {
      this.events = this.initEvents(events);
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

        this.eventService.deleteEvent(event._id).subscribe((events) => {
           this.loadEvents();
          this.refresh.next(1);
        });
      });
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
      console.log("response: ",responseEvent)
      this.eventService.createEvent({
        eventTitle: responseEvent.title,
        eventType: responseEvent.eventType,
        description: responseEvent.meta.notes,
        eventStartDate: responseEvent.start,
        eventEndDate: responseEvent.end,
        maxParticipants: responseEvent.maxParticipants,
        prizePool: responseEvent.prizePool

      }).subscribe((events) => {
        this.loadEvents()
        this.refresh.next(true);
      });
    });
  }

  public handleEvent(action: string, event: EgretCalendarEvent): void {
    // console.log(event)
    this.dialogRef = this.dialog.open(CalendarFormDialogComponent, {
      panelClass: 'calendar-form-dialog',
      data: { event, action },
      width: '450px',
    });

    this.dialogRef.afterClosed().subscribe((res) => {
      if (!res) {
        return;
      }
      let dialogAction = res.action;
      let responseEvent = res.event;
      console.log("responseUpdate:",responseEvent)
      if (dialogAction === 'save') {
        this.eventService.updateEvent(responseEvent._id, {
          eventTitle: responseEvent.title,
          eventType: responseEvent.eventType,
          description: responseEvent.meta.notes,
          eventStartDate: responseEvent.start,
          eventEndDate: responseEvent.end,
          maxParticipants: responseEvent.maxParticipants,
          prizePool: responseEvent.prizePool
  }).subscribe((events) => {
          this.loadEvents()
          this.refresh.next(1);
        });
      } else if (dialogAction === 'delete') {
        this.removeEvent(event);
      }
    });
  }

  public dayClicked({ date, events }: { date: Date; events: CalendarEvent[] }): void {
    if (isSameMonth(date, this.viewDate)) {
      if ((isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) || events.length === 0) {
        this.activeDayIsOpen = false;
      } else {
        this.activeDayIsOpen = true;
        this.viewDate = date;
      }
    }
  }

  public eventTimesChanged({ event, newStart, newEnd }: CalendarEventTimesChangedEvent): void {
    event.start = newStart;
    event.end = newEnd;

    this.calendarService.updateEvent(event).subscribe((events) => {
      this.events = this.initEvents(events);
      this.refresh.next(1);
    });
  }
}