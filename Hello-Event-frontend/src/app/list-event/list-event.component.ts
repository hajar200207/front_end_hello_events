import { Component, OnInit } from '@angular/core';
import { EventService } from '../event.service';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrls: ['./event-list.component.scss']
})
export class EventListComponent implements OnInit {
  events: any[] = [];

  constructor(private eventService: EventService) { }

  ngOnInit(): void {
    this.loadEvents();
  }

  loadEvents(): void {
    this.eventService.getAllEvents().subscribe(
      data => this.events = data,
      error => console.error('Error loading events', error)
    );
  }

  deleteEvent(id: number): void {
    this.eventService.deleteEvent(id).subscribe(
      () => this.loadEvents(),
      error => console.error('Error deleting event', error)
    );
  }
}
