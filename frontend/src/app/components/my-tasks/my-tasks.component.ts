import { Component, inject, OnInit, signal } from '@angular/core';
import { TasksService } from '../../services/tasks-service';
import {DatePipe} from '@angular/common';
import {TaskFormModal} from '../task-form-modal/task-form-modal';
import { TaskResponse } from '../../models/task.model';

@Component({
  selector: 'app-my-tasks',
  imports: [DatePipe, TaskFormModal],
  templateUrl: './my-tasks.component.html',
  styleUrl: './my-tasks.component.css',
})
export class MyTasksComponent implements OnInit {
  private taskService = inject(TasksService);
  tasks = signal<TaskResponse[]>([]);

  isModalVisible = false;
  selectedTask: TaskResponse | null = null;

  ngOnInit() {
    this.loadTasks();
  }

  deleteTask(id: number) {
    this.taskService.deleteTask(id).subscribe((res) => {
      if (res) {
        this.loadTasks();
      } else {
        console.log("Error when deleting task!")
      }
    });
  }

  openAddModal() {
    this.selectedTask = null;
    this.isModalVisible = true;
  }

  openEditModal(task: TaskResponse) {
    this.selectedTask = task;
    this.isModalVisible = true;
  }

  hideModal() {
    this.isModalVisible = false;
  }

  loadTasks() {
    this.taskService.getTasks().subscribe((res) => {
      this.tasks.set(res);
    });
  }

}
