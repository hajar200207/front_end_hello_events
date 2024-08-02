import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EventService } from '../event.service';
import { FormsModule } from '@angular/forms';


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
  editMode = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,  // Ajout du Router ici
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

  updateEvent() {
    if (this.event && this.event.id) {
      this.eventService.updateEvent(this.event.id, this.event).subscribe(
        (updatedEvent) => {
          console.log('Événement mis à jour avec succès', updatedEvent);
          this.event = updatedEvent;
          this.editMode = false;
        },
        (error) => {
          console.error('Erreur lors de la mise à jour de l\'événement', error);
        }
      );
    }
  }



  deleteEvent() {
    if (this.event && this.event.id) {
      if (confirm('Are you sure you want to delete this event?')) {
        this.eventService.deleteEvent(this.event.id).subscribe(
          () => {
            console.log('Event deleted successfully');
            this.router.navigate(['/events']);
          },
          (error) => {
            console.error('Error deleting event', error);
            this.error = 'Unable to delete event.';
          }
        );
      }
    }
  }
  toggleEditMode() {
    this.editMode = !this.editMode;
  }
}
