import { Component, EventEmitter, inject, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-register-component',
  imports: [ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export class RegisterComponent {
  private authService = inject(AuthService);
  private fb = inject(FormBuilder);
  message: string | null = null;

  @Output() registered = new EventEmitter<string>();

  registerForm: FormGroup = this.fb.group({
    username: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    birthDate: ['', Validators.required],
  });

  onSubmit() {
    this.message = null;
    this.handleRegister();
  }

  handleRegister() {
    if (this.registerForm.invalid) return;

    this.authService.register(this.registerForm.value).subscribe({
      next: (res) => {
        console.log('Registration successful', res);

        this.registered.emit(this.registerForm.value.email);
      },
      error: (err: HttpErrorResponse) => {
        console.error('Registration failed', err);

        this.message = err.error;
      },
    });

    this.registerForm.reset();
  }
}
