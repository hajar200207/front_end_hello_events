import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EventService } from '../event.service';

interface Event {
  id: number;
  name: string;
  dateTime: string;
  location: string;
  description: string;
  // Add other properties as per your event model
}

@Component({
  selector: 'app-event-details',
  templateUrl: './event-details.component.html',
  styleUrls: ['./event-details.component.scss']
})
export class EventDetailsComponent implements OnInit {
  event: Event | null = null;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private eventService: EventService
  ) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      const eventId = +params['id']; // Convert parameter to number
      this.loadEventDetails(eventId);
    });
  }

  loadEventDetails(id: number) {
    this.eventService.getEventDetails(id).subscribe(
      (eventData: Event) => {
        this.event = eventData;
      },
      (error) => {
        console.error('Error loading event details', error);
        this.error = 'Unable to load event details.';
      }
    );
  }
}
