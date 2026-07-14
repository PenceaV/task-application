import { Routes } from '@angular/router';
import { HomepageComponent } from './components/homepage/homepage.component';
import { MyTasksComponent } from './components/my-tasks/my-tasks.component';
import { Search } from './components/search/search';

export const routes: Routes = [
  { path: 'home', component: HomepageComponent },
  { path: 'tasks', component: MyTasksComponent },
  { path: 'search', component: Search},
  { path: '', redirectTo:'/home', pathMatch:'full'},
];
