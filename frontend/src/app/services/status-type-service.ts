import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { StatusType } from '../models/task.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class StatusTypeService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/statuses';

  getStatuses(): Observable<StatusType[]> {
    return this.http.get<StatusType[]>(this.apiUrl);
  }
}
