import { Component, inject, OnInit } from '@angular/core';
import { AuthService } from '../services/auth-service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-component',
  imports: [ReactiveFormsModule],
  templateUrl: './login-component.html',
  styleUrl: './login-component.css',
})
export class LoginComponent {
  private authService = inject(AuthService);
  private fb = inject(FormBuilder);
  private router = inject(Router);

  isRegisterMode = false;
  errorMessage: string | null = null;

  registerForm: FormGroup = this.fb.group({
    username: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    birthDate: ['', Validators.required]
  })

  authForm: FormGroup = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required],
  })

  onSubmit() {
    this.errorMessage = null;
    if (this.isRegisterMode) {
      if (this.registerForm.invalid) return;
      this.authService.register(this.registerForm.value).subscribe({
        next: (res) => {
          console.log('Registration successful', res);
          this.isRegisterMode = false;
          this.authForm.patchValue({
            email: this.registerForm.value.email,
            password: ''
          });
          this.errorMessage = 'Registration successful! Please login.';
          this.registerForm.reset();
        },
        error: (err) => {
          console.error('Registration failed', err);
          this.errorMessage = 'Registration failed. Please try again.';
          this.registerForm.reset();
        }
      });
    } else {
      if (this.authForm.invalid) return;
      this.authService.authenticate(this.authForm.value).subscribe({
        next: (res) => {
          console.log('Login successful', res);
          this.authService.setUser(res);
          this.router.navigate(['/tasks']);
          this.authForm.reset();
        },
        error: (err) => {
          console.error('Login failed', err);
          this.errorMessage = 'Login failed. Invalid email or password.';
          this.authForm.reset();
        }
      });
    }
  }

  toggleMode() {
    this.isRegisterMode = !this.isRegisterMode;
    this.errorMessage = null;
    this.authForm.reset();
    this.registerForm.reset();
  }
}
