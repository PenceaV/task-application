import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Credentials, LoginResponse, User } from '../models/task.model';
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/auth';

  currentUser = signal<string | null>(null);

  constructor() {
    const token = localStorage.getItem('token');
    if (token) {
      this.currentUser.set(token);
    }
  }

  login(credentials: Credentials): Observable<LoginResponse> {

    const encodedCredentials = {
      email: btoa(credentials.email),
      password: btoa(credentials.password),
    };

    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, encodedCredentials);
  }

  register(user: any): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/register`, user);
  }

  setToken(token: string) {
    localStorage.setItem('token', token)
    this.currentUser.set(token);
  }

  logout() {
    localStorage.removeItem('token');
    this.currentUser.set(null);
  }
}
