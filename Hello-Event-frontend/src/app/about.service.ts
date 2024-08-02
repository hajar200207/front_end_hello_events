import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AboutService {

  private apiUrl = 'http://localhost:8082/api/events';

  constructor(private http: HttpClient) { }

  getAboutInfo(): Observable<any> {
    return this.http.get(`${this.apiUrl}/about`, { responseType: 'text' });
  }
}
