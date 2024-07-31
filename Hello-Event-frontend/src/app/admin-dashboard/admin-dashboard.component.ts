import { Component } from '@angular/core';
import { Event } from './event.model';
import { EventService } from '../services/event.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent {
  event: Event = { name: '', date: '', location: '', description: '' };

  constructor(private eventService: EventService) {}

  createEvent() {
    this.eventService.createEvent(this.event).subscribe(
      response => {
        console.log('Event created successfully', response);
        // Handle successful creation, e.g., show a message or redirect
      },
      error => {
        console.error('Error creating event', error);
        // Handle error, e.g., show an error message
      }
    );
  }
}
