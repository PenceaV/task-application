import { Routes } from '@angular/router';
import { HomepageComponent } from './components/homepage/homepage.component';
import { MyTasksComponent } from './components/my-tasks/my-tasks.component';
import { LoginComponent } from './components/login/login.component';
import { loggedInGuard } from './services/logged-in.guard';

export const routes: Routes = [
  { path: 'home', component: HomepageComponent, canActivate: [loggedInGuard] },
  { path: 'tasks', component: MyTasksComponent, canActivate: [loggedInGuard] },
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: '**', redirectTo: 'home'}
];
