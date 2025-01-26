import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email = new FormControl('', [Validators.required, Validators.email]);

  updateErrorMessage() {
    // Logic to update error messages
  }

  errorMessage() {
    if (this.email.hasError('required')) {
      return 'You must enter a value';
    }
    if (this.email.hasError('email')) {
      return 'Not a valid email';
    }
    return '';
  }

  hide = () => true; // Example logic for hide toggle
  clickEvent(event: Event) {
    // Logic for handling password visibility toggle
  }

}
