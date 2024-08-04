import { Component, OnInit } from '@angular/core';
import { EventService } from '../event.service';

@Component({
  selector: 'app-list-event',
  templateUrl: './list-event.component.html',
  styleUrls: ['./list-event.component.scss']
})
export class ListEventComponent implements OnInit {
  events: any[] = [];

  constructor(private eventService: EventService) { }

  ngOnInit(): void {
    this.loadEvents();
  }

  loadEvents(): void {
    this.eventService.getEvents().subscribe(
      (data: any[]) => this.events = data,
      (error: any) => console.error('Error loading events', error)
    );
  }

  deleteEvent(id: number): void {
    this.eventService.deleteEvent(id).subscribe(
      () => this.loadEvents(),
      (error: any) => console.error('Error deleting event', error)
    );
  }
}
