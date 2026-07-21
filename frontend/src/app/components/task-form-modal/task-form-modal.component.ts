import {
  Component,
  EventEmitter,
  inject,
  Input,
  OnChanges,
  OnInit,
  Output,
  signal,
} from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import {
  StatusType,
  TaskCreateRequest,
  TaskResponse,
  TaskUpdateRequest,
  User,
} from '../../models/task.model';
import { TasksService } from '../../services/tasks.service';
import { UserService } from '../../services/user.service';
import { StatusTypeService } from '../../services/status-type.service';

@Component({
  selector: 'app-task-form-modal',
  imports: [ReactiveFormsModule],
  templateUrl: './task-form-modal.component.html',
  styleUrl: './task-form-modal.component.css',
  standalone: true,
})
export class TaskFormModal implements OnInit, OnChanges {
  private taskService = inject(TasksService);
  private userService = inject(UserService);
  private statusService = inject(StatusTypeService);
  private fb = inject(FormBuilder);

  @Input() task: TaskResponse | null = null;
  @Output() close = new EventEmitter<void>();
  @Output() saved = new EventEmitter<void>();

  isEditMode = false;
  private currentTaskId: number | null = null;

  taskForm: FormGroup = this.fb.group({
    taskName: ['', Validators.required],
    userId: [null, Validators.required],
    statusTypeId: ['', Validators.required],
    dueDate: [null, Validators.required],
  });

  users = signal<User[]>([]);
  statusTypes = signal<StatusType[]>([]);

  ngOnInit(): void {
    this.userService.getUsers().subscribe((res) => {
      this.users.set(res);
    });

    this.statusService.getStatuses().subscribe((res) => {
      this.statusTypes.set(res);
    });
  }

  ngOnChanges(): void {
    if (this.task) {
      this.isEditMode = true;
      this.currentTaskId = this.task.taskId;
      this.taskForm.patchValue({
        taskName: this.task.taskName,
        userId: this.task.userId,
        statusTypeId: this.task.statusType.statusTypeId,
        dueDate: this.task.dueDate,
      });
    } else {
      this.isEditMode = false;
      this.currentTaskId = null;
      this.taskForm.reset({
        taskName: '',
        userId: null,
        statusTypeId: '',
        dueDate: null,
      });
    }
  }

  onSubmit(): void {
    if (this.taskForm.invalid) {
      return;
    }

    if (this.isEditMode) {
      const updatePayload: TaskUpdateRequest = {
        taskName: this.taskForm.value.taskName,
        userId: this.taskForm.value.userId,
        statusTypeId: this.taskForm.value.statusTypeId,
        dueDate: this.taskForm.value.dueDate,
      };

      this.taskService.updateTask(this.currentTaskId!, updatePayload).subscribe({
        next: () => {
          this.saved.emit();
          this.close.emit();
        },
        error: (err) => {
          console.error('Error updating task: ', err);
        },
      });
    } else {
      const createPayload: TaskCreateRequest = this.taskForm.value;
      this.taskService.createTask(createPayload).subscribe({
        next: () => {
          this.saved.emit();
          this.close.emit();
        },
        error: (err) => {
          console.error('Error creating task: ', err);
        },
      });
    }
  }

  closeModal(): void {
    this.close.emit();
  }
}
