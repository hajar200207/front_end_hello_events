import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';
import { HttpErrorResponse } from '@angular/common/http';
import { EventService } from '../event.service';

interface Event {
  id: number;
  name: string;
  date: string | Date;
  location: string;
  isUserEvent?: boolean;
  description: string;
}

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})

export class AdminDashboardComponent implements OnInit {
  userInfo: any;
  users: any[] = [];
  events: Event[] = [];
  error: string | null = null;  // Add this property to handle errors

  constructor(
    private authService: AuthService,
    private eventService: EventService
  ) { }

  ngOnInit() {
    this.loadAdminInfo();
    this.loadUsers();
    this.loadAllEvents();  // Ensure this is the correct method name
  }

  loadAdminInfo() {
    const username = this.authService.getUsername();
    if (username) {
      this.authService.getUserInfo(username).subscribe(
        info => {
          this.userInfo = info;
        },
        error => {
          console.error('Erreur lors de la récupération des informations utilisateur', error);
        }
      );
    }
  }

  loadUsers() {
    this.authService.getAllUsers().subscribe(
      users => {
        this.users = users;
      },
      error => {
        console.error('Erreur lors du chargement des utilisateurs', error);
      }
    );
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

  updateUser(user: any) {
    this.authService.updateUser(user).subscribe(
      updatedUser => {
        console.log('Utilisateur mis à jour', updatedUser);
        this.loadUsers();
      },
      error => {
        console.error('Erreur lors de la mise à jour de l\'utilisateur', error);
      }
    );
  }

  deleteUser(id: number) {
    this.authService.deleteUser(id).subscribe(
      () => {
        console.log('Utilisateur supprimé');
        this.loadUsers();
      },
      error => {
        console.error('Erreur lors de la suppression de l\'utilisateur', error);
      }
    );
  }

  deleteEvent(id: number) {
    if (confirm('Are you sure you want to delete this event?')) {
      this.eventService.deleteEvent(id).subscribe(
        () => {
          console.log('Event deleted successfully');
          this.loadAllEvents();  // Call the correct method to reload events
        },
        (error) => {
          console.error('Error deleting event', error);
          this.error = 'Unable to delete event.';  // Store the error message
        }
      );
    }
  }
}
