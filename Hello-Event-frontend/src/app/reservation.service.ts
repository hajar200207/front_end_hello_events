
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Reservation} from "./reservation.model";


@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private baseUrl = 'http://localhost:8080/reservations';

  constructor(private http: HttpClient) {}

  createReservation(userEmail: string, eventId: number, numberOfTickets: number, lastUpdated: string): Observable<Reservation> {
    const params = { userEmail, eventId, numberOfTickets, lastUpdated };
    return this.http.post<Reservation>(`${this.baseUrl}`, params);
  }
}
