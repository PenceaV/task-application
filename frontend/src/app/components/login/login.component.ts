import { Component, inject, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { RegisterComponent } from '../register/register.component';

@Component({
  selector: 'app-login-component',
  imports: [ReactiveFormsModule, RegisterComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  standalone: true,
})
export class LoginComponent {
  private authService = inject(AuthService);
  private fb = inject(FormBuilder);
  private router = inject(Router);

  isLogin = true;
  message: string | null = null;

  loginForm: FormGroup = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required],
  });

  onSubmit() {
    this.message = null;
    this.handleLogin();
  }

  handleLogin() {
    if (this.loginForm.invalid) return;

    this.authService.login(this.loginForm.value).subscribe({
      next: (res) => {
        console.log('Login successful');

        this.authService.setToken(res.token);
        this.router.navigate(['/tasks']);
      },
      error: (err) => {
        console.error('Login failed', err);

        this.message = 'Login failed. Invalid email or password.';
      },
    });
    this.loginForm.reset();
  }

  onUserRegistered(email: string) {
    this.isLogin = true;

    this.loginForm.patchValue({
      email: email,
      password: ''
    });

    this.message = 'Registration successful! Please login.';
  }

  toggleMode() {
    this.isLogin = !this.isLogin;
    this.message = null;
    this.loginForm.reset();
  }
}
