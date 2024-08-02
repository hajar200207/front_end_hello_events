import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';
import { EventService } from '../event.service';
import { Reservation } from '../reservation.model';
import { ContactService } from '../contact.service';
import { Contact, ContactStatus } from '../contact.model';
import {HttpHeaders} from "@angular/common/http";

interface User {
  id: number;
  username: string;
  email: string;
  role?: string; // Make role optional
}

interface Event {
  id: number;
  name: string;
  date: string | Date;
  location: string;
  isUserEvent?: boolean;
  description: string;
}

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css']
})
export class UserDashboardComponent implements OnInit {
  userInfo: User | null = null;
  updatedUserInfo: Partial<User> = {};
  currentView: string = 'profile';
  events: Event[] = [];
  selectedEvents: Event[] = [];
  date: string = '';
  location: string = '';
  keyword: string = '';
  showReservationForm: boolean = false;
  selectedEventId: number | null = null;
  numberOfTickets: number = 1;
  reservations: any[] = [];
  contacts: Contact[] = [];
  newContact: Contact = { id: 0, name: '', email: '', subject: '', message: '', submissionTime: new Date(), status: ContactStatus.NEW };

  constructor(
    private authService: AuthService,
    private eventService: EventService,
    private contactService: ContactService
  ) { }

  ngOnInit() {
    this.loadUserInfo();
    this.loadAllEvents();
    this.loadUserReservations();
    this.loadAllContacts();
  }

  parseDate(dateString: string): Date {
    const [datePart, timePart] = dateString.split(' ');
    const [day, month, year] = datePart.split('/').map(Number);
    const [hours, minutes] = timePart.split(':').map(Number);
    return new Date(year, month - 1, day, hours, minutes);
  }

  loadUserInfo() {
    const username = this.authService.getUsername();
    if (username) {
      this.authService.getUserInfo(username).subscribe(
        info => {
          this.userInfo = info;
          this.updatedUserInfo = { ...info };
        },
        error => {
          console.error('Erreur lors de la récupération des informations utilisateur', error);
        }
      );
    }
  }

  updateUser() {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`);
    this.authService.updateUser(this.updatedUserInfo, { headers }).subscribe(
      (response: User) => {
        console.log('User updated successfully', response);
        this.userInfo = response;  // Update the view with new user data
      },
      error => {
        if (error.status === 403) {
          console.error('Access is forbidden: Check your token and user permissions.');
        } else {
          console.error('An error occurred:', error);
        }
      }
    );
  }





  changeView(view: string) {
    this.currentView = view;
  }

  loadAllEvents() {
    this.eventService.getEvents().subscribe(
      (events: Event[]) => {
        console.log('Events reçus:', events);
        events.forEach(event => {
          console.log('Date brute de l\'événement:', event.date);
        });
        this.events = events;
      },
      error => {
        console.error('Erreur lors du chargement des événements', error);
      }
    );
  }

  toggleEventSelection(event: Event) {
    const index = this.selectedEvents.findIndex(e => e.id === event.id);
    if (index > -1) {
      this.selectedEvents.splice(index, 1);
    } else {
      this.selectedEvents.push(event);
    }
  }

  saveHomePageEvents() {
    this.eventService.saveHomePageEvents(this.selectedEvents).subscribe(
      response => {
        console.log('Home page events saved successfully', response);
      },
      error => {
        console.error('Error saving home page events', error);
      }
    );
  }

  searchEvents() {
    this.eventService.searchEvents(this.date, this.location, this.keyword).subscribe(
      (events: Event[]) => {
        console.log('Résultats de la recherche:', events);
        this.events = events;
      },
      error => {
        console.error('Erreur lors de la recherche des événements', error);
      }
    );
  }

  bookEvent(eventId: number) {
    this.selectedEventId = eventId;
    this.showReservationForm = true;
  }

  reserveEvent() {
    const reservation = {
      eventId: this.selectedEventId,
      numberOfTickets: this.numberOfTickets
    };

    if (this.selectedEventId) {
      this.eventService.createReservation(reservation).subscribe(
        response => {
          console.log('Reservation successful', response);
          this.showReservationForm = false;
        },
        error => {
          console.error('Error making reservation', error);
        }
      );
    }
  }

  loadUserReservations() {
    this.eventService.getUserReservations().subscribe(
      (reservations: Reservation[]) => {
        this.reservations = reservations;
      },
      error => {
        console.error('Error loading reservations', error);
      }
    );
  }

  cancelReservation() {
    this.showReservationForm = false;
    this.selectedEventId = null;
    this.numberOfTickets = 1;
  }

  loadAllContacts() {
    this.contactService.getAllContacts().subscribe(
      (contacts: Contact[]) => {
        this.contacts = contacts;
      },
      error => {
        console.error('Erreur lors du chargement des contacts', error);
      }
    );
  }





  deleteContact(id: number) {
    this.contactService.deleteContact(id).subscribe(
      () => {
        this.contacts = this.contacts.filter(contact => contact.id !== id);
      },
      error => {
        console.error('Erreur lors de la suppression du contact', error);
      }
    );
  }
}
