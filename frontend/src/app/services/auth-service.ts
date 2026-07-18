import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Credentials, User } from '../models/task.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/auth';

  currentUser = signal<User | null>(null);

  authenticate(credentials: Credentials): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/authenticate`, credentials);
  }

  register(user: any): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/register`, user);
  }

  setUser(user: User) {
    localStorage.setItem('user', user.username)
    this.currentUser.set(user);
  }

  logout() {
    this.currentUser.set(null);
  }
}
