import { Component, OnInit } from '@angular/core';
import { EventService } from '../event.service';
import { trigger, state, style, animate, transition } from '@angular/animations';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  animations: [
    trigger('buttonAnimation', [
      state('hidden', style({
        opacity: 0,
        transform: 'scale(0.8)'
      })),
      state('visible', style({
        opacity: 1,
        transform: 'scale(1)'
      })),
      transition('hidden => visible', [
        animate('300ms ease-out')
      ]),
      transition('visible => hidden', [
        animate('300ms ease-in')
      ])
    ])
  ]
})
export class HomeComponent implements OnInit {
  events: any[] = [];
  selectedEventId: number | null = null;

  constructor(private eventService: EventService,private router: Router) { }

  ngOnInit(): void {
    this.loadEvents();
  }

  loadEvents(): void {
    this.eventService.getEvents().subscribe(
      (data) => {
        this.events = data;
      },
      (error) => {
        console.error('Erreur lors du chargement des événements', error);
      }
    );
  }

  toggleButtons(eventId: number): void {
    this.selectedEventId = this.selectedEventId === eventId ? null : eventId;
  }
  navigateToInscription(): void {
    this.router.navigate(['/register']);
  }
}
