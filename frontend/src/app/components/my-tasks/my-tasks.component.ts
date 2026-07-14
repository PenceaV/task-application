import { Component, inject, OnInit, signal } from '@angular/core';
import { TasksService } from '../../services/tasks-service';

@Component({
  selector: 'app-my-tasks',
  imports: [],
  templateUrl: './my-tasks.component.html',
  styleUrl: './my-tasks.component.css',
})
export class MyTasksComponent implements OnInit {
  private taskService = inject(TasksService);
  tasks = signal<any[]>([]);

  ngOnInit() {
    this.taskService.getTasks().subscribe((res) => {
      this.tasks.set(res);
    });
  }
}
