/*import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  private apiUrl = 'http://localhost:8082/api/events';

  constructor(private http: HttpClient) { }

  getEvents(): Observable<any> {
    return this.http.get(`${this.apiUrl}`).pipe(
      catchError(error => {
        console.error('Erreur brute:', error);
        return throwError('Erreur lors du chargement des événements. Veuillez réessayer.');
      })
    );
  }

  searchEvents(date: string, location: string, keyword: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/search`, { params: { date, location, keyword } });
  }

  getEventDetails(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  bookEvent(eventId: number, numberOfTickets: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/${eventId}/book`, { numberOfTickets });
  }


  getAboutInfo(): Observable<any> {
    return this.http.get(`${this.apiUrl}/about`);
  }

  submitContact(contactData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/contact`, contactData);
  }
  saveHomePageEvents(selectedEvents: any[]): Observable<any> {
    return this.http.post(`${this.apiUrl}/home-events`, selectedEvents);
  }
  getUserEvents(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/user/${userId}`);
  }
}
*/
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { AuthService } from './auth.service';
import {Reservation} from "./reservation.model";

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private apiUrl = 'http://localhost:8082/api/events';
  private reservationApi = 'http://localhost:8082/api';

  constructor(private http: HttpClient, private authService: AuthService) { } // Corrected injection

  getEvents(): Observable<any> {
    return this.http.get(`${this.apiUrl}`).pipe(
      catchError(error => {
        console.error('Erreur brute:', error);
        return throwError('Erreur lors du chargement des événements. Veuillez réessayer.');
      })
    );
  }

  searchEvents(date: string, location: string, keyword: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/search`, { params: { date, location, keyword } });
  }

  getEventDetails(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }


  createReservation(eventId: number, numberOfTickets: number): Observable<any> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    const body = {
      eventId: eventId,
      numberOfTickets: numberOfTickets
    };

    return this.http.post<any>(`${this.reservationApi}/reservation`, body, { headers, responseType: 'json' }).pipe(
      catchError(error => {
        console.error('Error making reservation:', error);
        return throwError('Error making reservation. Please try again.');
      })
    );
  }
  getUserReservations(): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.reservationApi}/reservations`);
  }




  getAboutInfo(): Observable<any> {
    return this.http.get(`${this.apiUrl}/about`);
  }

  submitContact(contactData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/contact`, contactData);
  }

  saveHomePageEvents(selectedEvents: any[]): Observable<any> {
    return this.http.post(`${this.apiUrl}/home-events`, selectedEvents);
  }

  getUserEvents(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/user/${userId}`);
  }
}
