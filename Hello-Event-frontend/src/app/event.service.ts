import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { AuthService } from './auth.service';
import { Reservation } from './reservation.model';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  private apiUrl = 'http://localhost:8082/api/events';
  private reservationApi = 'http://localhost:8082/api/reservations';

  constructor(private http: HttpClient, private authService: AuthService) { }

  // Get all events
  getEvents(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}`).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('Erreur lors du chargement des événements:', error);
        return throwError('Erreur lors du chargement des événements. Veuillez réessayer.');
      })
    );
  }

  // Search for events with filters
  searchEvents(date: string, location: string, keyword: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/search`, { params: { date, location, keyword } }).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('Erreur lors de la recherche des événements:', error);
        return throwError('Erreur lors de la recherche des événements. Veuillez réessayer.');
      })
    );
  }

  // Get event details by ID
  getEventDetails(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('Erreur lors du chargement des détails de l’événement:', error);
        return throwError('Erreur lors du chargement des détails de l’événement. Veuillez réessayer.');
      })
    );
  }

  // Create a new reservation
  createReservation(reservation: Reservation): Observable<Reservation> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.post<Reservation>(`${this.reservationApi}`, reservation, { headers }).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('Erreur lors de la réservation:', error);
        return throwError('Erreur lors de la réservation. Veuillez réessayer.');
      })
    );
  }

  // Get reservations for the logged-in user
  getUserReservations(): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.reservationApi}`).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('Erreur lors du chargement des réservations:', error);
        return throwError('Erreur lors du chargement des réservations. Veuillez réessayer.');
      })
    );
  }

  // Get information about the application or company
  getAboutInfo(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/about`).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('Erreur lors du chargement des informations:', error);
        return throwError('Erreur lors du chargement des informations. Veuillez réessayer.');
      })
    );
  }

  // Submit a contact form
  submitContact(contactData: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/contact`, contactData).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('Erreur lors de l’envoi du contact:', error);
        return throwError('Erreur lors de l’envoi du contact. Veuillez réessayer.');
      })
    );
  }

  // Save selected events for the home page
  saveHomePageEvents(selectedEvents: any[]): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/home-events`, selectedEvents).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('Erreur lors de la sauvegarde des événements:', error);
        return throwError('Erreur lors de la sauvegarde des événements. Veuillez réessayer.');
      })
    );
  }

  // Get events associated with a specific user
  getUserEvents(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/user/${userId}`).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('Erreur lors du chargement des événements de l’utilisateur:', error);
        return throwError('Erreur lors du chargement des événements de l’utilisateur. Veuillez réessayer.');
      })
    );
  }

  // Create a new event
  createEvent(event: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, event).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('Erreur lors de la création de l’événement:', error);
        return throwError('Erreur lors de la création de l’événement. Veuillez réessayer.');
      })
    );
  }
  updateEvent(id: number, event: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, event).pipe(
      catchError(this.handleError)
    );
  }

  // Nouvelle méthode de suppression d'événement


  private handleError(error: HttpErrorResponse) {
    console.error('An error occurred:', error);
    return throwError('Something went wrong. Please try again later.');
  }
  deleteEvent(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }


}
