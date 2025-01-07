import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA as MAT_DIALOG_DATA, MatDialogRef as MatDialogRef } from '@angular/material/dialog';
import { CalendarEvent } from 'angular-calendar';
import { UntypedFormBuilder, UntypedFormControl, UntypedFormGroup, Validators } from '@angular/forms';
import { EgretCalendarEvent } from '../../../shared/models/event.model';

interface DialogData {
  event?: CalendarEvent,
  action?: string,
  date?: Date
}

@Component({
  selector: 'app-calendar-form-dialog',
  templateUrl: './calendar-form-dialog.component.html',
  styleUrls: ['./calendar-form-dialog.component.scss']
})
export class CalendarFormDialogComponent implements OnInit {
  event: CalendarEvent;
  dialogTitle: string;
  eventForm: UntypedFormGroup;
  action: string;
  eventTypes: string[] = ['TRADING_COMPETITION', 'TRAINING_SESSION', 'WORKSHOP', 'CONFERENCE'];

  constructor(
    public dialogRef: MatDialogRef<CalendarFormDialogComponent>,
    @Inject(MAT_DIALOG_DATA) private data: DialogData,
    private formBuilder: UntypedFormBuilder
  ) {
    this.event = data.event;
    this.action = data.action;
    
    if (this.action === 'edit') {
      this.dialogTitle = this.event.title.toUpperCase();
    } else {
      this.dialogTitle = 'Add Event';
      this.event = new EgretCalendarEvent({
        start: data.date,
        end: data.date
      });
    }
    // console.log(data);
    this.eventForm = this.buildEventForm(this.event);
  }

  ngOnInit() {
  }

  buildEventForm(event: EgretCalendarEvent) {
    return new UntypedFormGroup({
      _id: new UntypedFormControl(event._id),
      title: new UntypedFormControl(event.title.replace(/\s*\(.*?\)\s*/g, '').trim(), [Validators.required]),
      start: new UntypedFormControl(event.start, [Validators.required]),
      end: new UntypedFormControl(event.end, [Validators.required]),
      eventType: new UntypedFormControl(event.meta?.eventType || 'TRADING_COMPETITION', [
        Validators.required,
      ]),
      maxParticipants: new UntypedFormControl(event.meta?.maxParticipants || 1, [
        Validators.required,
        Validators.min(1),
      ]),
      prizePool: new UntypedFormControl(event.meta?.prizePool || 1, [
        Validators.required,
        Validators.min(1),
      ]),
      color: this.formBuilder.group({
        primary: new UntypedFormControl(event.color.primary),
        secondary: new UntypedFormControl(event.color.secondary),
      }),
      meta: this.formBuilder.group({
        notes: new UntypedFormControl(event.meta?.notes || '', [Validators.required]),
      }),
    });
  }
  
  

}
