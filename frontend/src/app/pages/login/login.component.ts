import { Component } from '@angular/core';
import {AuthenticationRequest} from "../../services/models/authentication-request";
import {AuthenticationService} from "../../services/services/authentication.service";
import {Router} from "@angular/router";
import {AuthenticationResponse} from "../../services/models/authentication-response";
import {TokenService} from "../../services/token/token.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  authRequest: AuthenticationRequest={email: '',password:''}; // will hold my credentials
  errorMsg: Array<string> =[]; //save the error in case
  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private tokenService: TokenService
  ){}
  login() {
    this.errorMsg=[];
    this.authService.authenticate(
      {
        body: this.authRequest
      }
    ).subscribe({
      next:(res:AuthenticationResponse):void =>{
        console.log('authentification success');
        // todo save the token
        this.tokenService.token=res.token as string;
        this.router.navigate(['task-management']);
      },
      error:(err):void=>{
        console.log('error authentification');
        console.log(err);
        if (err.error.validationErrors) {
          this.errorMsg = err.error.validationErrors;
        } else {
          this.errorMsg.push(err.error.error);
        }
      }
    });

  }

  register() {
    this.router.navigate(['register']);


  }
}
