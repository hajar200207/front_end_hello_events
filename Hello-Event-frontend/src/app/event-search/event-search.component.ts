import { Component } from '@angular/core';
import { EventService } from '../event.service';

@Component({
  selector: 'app-event-search',
  templateUrl: './event-search.component.html',
  styleUrls: ['./event-search.component.css']
})
export class EventSearchComponent {
  date: string = '';
  location: string = '';
  keyword: string = '';
  events: any[] = [];

  constructor(private eventService: EventService) { }

  searchEvents() {
    this.eventService.searchEvents(this.date, this.location, this.keyword)
      .subscribe(
        (data) => {
          this.events = data;
        },
        (error) => {
          console.error('Error fetching events', error);
        }
      );
  }
}
