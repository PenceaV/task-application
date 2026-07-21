import { Component, computed, inject, signal } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class App {
  protected authService = inject(AuthService);
  protected readonly title = signal('task-app');

  logout() {
    this.authService.logout();
    location.reload();
  }
}
