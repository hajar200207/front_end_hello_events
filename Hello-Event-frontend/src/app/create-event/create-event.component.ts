import { Component } from '@angular/core';
import { EventService } from '../event.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-event-create',
  templateUrl: './event-create.component.html',
  styleUrls: ['./event-create.component.scss']
})
export class EventCreateComponent {
  event = { name: '', dateTime: '', location: '', description: '' };

  constructor(private eventService: EventService, private router: Router) { }

  createEvent(): void {
    this.eventService.createEvent(this.event).subscribe(
      () => this.router.navigate(['/events']),
      error => console.error('Error creating event', error)
    );
  }
}
