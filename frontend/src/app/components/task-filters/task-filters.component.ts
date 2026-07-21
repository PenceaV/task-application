import { Component, EventEmitter, inject, Input, OnInit, Output, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { StatusTypeService } from '../../services/status-type.service';
import { UserService } from '../../services/user.service';
import { StatusType, User } from '../../models/task.model';
import { TaskFilters } from '../../models/task-filters.model';

@Component({
  selector: 'app-task-filters',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './task-filters.component.html',
  styleUrl: './task-filters.component.css',
})
export class TaskFiltersComponent implements OnInit {
  private statusTypeService = inject(StatusTypeService);
  private userService = inject(UserService);

  @Input() initialFilters?: TaskFilters;
  @Output() filterChanged = new EventEmitter<TaskFilters>();
  @Output() close = new EventEmitter<void>();

  filters: TaskFilters = {
    taskName: '',
    assignedTo: '',
    status: '',
    dueDate: '',
    sortDir: 'desc',
  };

  statuses = signal<StatusType[]>([]);
  users = signal<User[]>([]);

  ngOnInit() {
    if (this.initialFilters) this.filters = { ...this.initialFilters };
    this.statusTypeService.getStatuses().subscribe((statuses) => this.statuses.set(statuses));
    this.userService.getUsers().subscribe((users) => this.users.set(users));
  }

  applyFilters() {
    this.filterChanged.emit({ ...this.filters });
  }

  resetFilters() {
    this.filters = {
      taskName: '',
      assignedTo: '',
      status: '',
      dueDate: '',
      sortDir: 'desc',
    };
    this.applyFilters();
  }

  closeSidebar() {
    this.close.emit();
  }
}
