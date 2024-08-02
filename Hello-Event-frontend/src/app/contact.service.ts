import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { Contact, ContactStatus } from './contact.model';

@Injectable({
  providedIn: 'root'
})
export class ContactService {
  private apiUrl = 'http://localhost:8082/contacts';

  constructor(private http: HttpClient, private authService: AuthService){}
  createContact(contact: Contact, options: { headers: HttpHeaders }): Observable<Contact> {
    return this.http.post<Contact>(`${this.apiUrl}`, contact, options);
  }



  getAllContacts(): Observable<Contact[]> {
    return this.http.get<Contact[]>(`${this.apiUrl}`);
  }

  getContactById(id: number): Observable<Contact> {
    return this.http.get<Contact>(`${this.apiUrl}/${id}`);
  }

  updateContactStatus(id: number, status: ContactStatus): Observable<Contact> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.getToken()}`
    });

    return this.http.patch<Contact>(`${this.apiUrl}/${id}/status?status=${status}`, null, { headers });
  }

  deleteContact(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
