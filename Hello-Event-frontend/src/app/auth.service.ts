import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

interface User {
  id: number;
  username: string;
  email: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8082/api/users';

  constructor(private http: HttpClient) { }
  private getHeaders() {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }
  login(username: string, password: string): Observable<{token: string, role: string}> {
    return this.http.post<{token: string, role: string}>(`${this.apiUrl}/login`, { username, password }).pipe(
      tap(response => {
        if (response.token) {
          localStorage.setItem('token', response.token);
          localStorage.setItem('userRole', response.role);
          localStorage.setItem('username', username);
        }
      })
    );
  }
  getUserInfo(username: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/${username}`);
  }

  getUsername(): string | null {
    return localStorage.getItem('username');
  }

  getUserRole(): string | null {
    return localStorage.getItem('userRole');
  }
  register(user: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, user);
  }

  logout() {
    localStorage.removeItem('token');
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }


  getAllUsers(): Observable<any> {
    return this.http.get(`${this.apiUrl}/all`);
  }
  deleteUser(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

  updateUser(user: User): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.put(`${this.apiUrl}/update`, user, { headers });
  }

}
