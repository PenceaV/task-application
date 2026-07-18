import { Component, inject, OnInit, signal } from '@angular/core';
import { TasksService } from '../../services/tasks-service';
import { CommonModule, DatePipe } from '@angular/common';
import { TaskFormModal } from '../task-form-modal/task-form-modal';
import { TaskResponse } from '../../models/task.model';
import { TaskFilters } from '../../models/task-filters.model';
import { TaskFiltersComponent } from '../task-filters/task-filters.component';

@Component({
  selector: 'app-my-tasks',
  standalone: true,
  imports: [CommonModule, DatePipe, TaskFormModal, TaskFiltersComponent],
  templateUrl: './my-tasks.component.html',
  styleUrl: './my-tasks.component.css',
})
export class MyTasksComponent implements OnInit {
  private taskService = inject(TasksService);

  tasks = signal<TaskResponse[]>([]);

  // Filter properties
  currentFilters: TaskFilters = {
    taskName: '',
    assignedTo: '',
    status: '',
    dueDate: '',
    sortDir: 'desc',
  };

  isSidebarVisible = false;
  isModalVisible = false;
  selectedTask: TaskResponse | null = null;

  ngOnInit() {
    this.taskService.getTasks().subscribe((res) => {
      this.tasks.set(res);
    });
  }

  deleteTask(id: number) {
    this.taskService.deleteTask(id).subscribe((res) => {
      if (res) {
        this.onSearch();
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

  toggleSidebar() {
    this.isSidebarVisible = !this.isSidebarVisible;
  }

  onSearch(filters?: TaskFilters) {
    if (filters) {
      this.currentFilters = filters;
    }

    this.taskService
      .searchTasks({
        subject: this.currentFilters.taskName,
        assignedTo: this.currentFilters.assignedTo,
        status: this.currentFilters.status,
        dueDate: this.currentFilters.dueDate,
        sortDir: this.currentFilters.sortDir,
      })
      .subscribe((res) => {
        this.tasks.set(res);
      });
  }

  toggleSort() {
    this.currentFilters.sortDir = this.currentFilters.sortDir === 'asc' ? 'desc' : 'asc';
    this.onSearch();
  }
}
