import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;

  errorMessage: string = '';
  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}
  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: [
        '',
        [Validators.required, Validators.email] // Email validation
      ],
      password: [
        '',
        [Validators.required, Validators.minLength(6)] // Password validation
      ]
    });

  }
  onSubmit(): void {
    if (this.loginForm.invalid) {
      this.errorMessage = 'Please fill in all required fields correctly.';
      return;
    }

    const { email, password } = this.loginForm.value;

    this.authService.login(email, password).subscribe(
      (response) => {
        // Save token and navigate
        this.authService.saveToken(response.token);
        this.router.navigate(['/dashboard']);
      },
      (error) => {
        // Handle login error
        this.errorMessage = 'Invalid email or password';
        console.error(error);
      }
    );
  }

  // Get the error message based on control validation
  getErrorMessage(): string {
    if (this.email?.hasError('required')) {
      return 'Email is required';
    } else if (this.email?.hasError('email')) {
      return 'Please enter a valid email';
    } else if (this.password?.hasError('required')) {
      return 'Password is required';
    } else if (this.password?.hasError('minlength')) {
      return 'Password must be at least 6 characters long';
    }
    return '';
  }

  // Update the error message based on the control's current status
  updateErrorMessage() {
    if (this.email?.touched && this.email?.invalid) {
      this.errorMessage = this.getErrorMessage();
    }
  }
}

}
