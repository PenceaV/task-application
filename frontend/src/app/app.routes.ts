import { Routes } from '@angular/router';
import { HomepageComponent } from './components/homepage/homepage.component';
import { MyTasksComponent } from './components/my-tasks/my-tasks.component';
import { LoginComponent } from './login-component/login-component';
import { LoggedInGuardService } from './services/logged-in-guard-service';

export const routes: Routes = [
  { path: 'home', component: HomepageComponent, canActivate: [LoggedInGuardService] },
  { path: 'tasks', component: MyTasksComponent, canActivate: [LoggedInGuardService] },
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
];
