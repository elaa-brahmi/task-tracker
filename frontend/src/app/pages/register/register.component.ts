import { Component } from '@angular/core';
import {RegistrationRequest} from "../../services/models/registration-request";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerRequest: RegistrationRequest={email:'',firstName:'',lastName:'',password:''};
  errorMsg:Array<String>=[];
  constructor(
    private router:Router,
    private authService:AuthenticationService

  ){}

  login() {
    this.router.navigate(['login']);

  }
  register(){
    this.errorMsg=[];
    this.authService.register({
      body:this.registerRequest
    }).subscribe({
      next:()=>{
        this.router.navigate(['activate-account']);

      },
      error:(err):void=>{
        if(err.error.validationErrors){
          this.errorMsg=err.error.validationErrors;

        }
        else{
          this.errorMsg.push(err.error.error);
        }
      }
    })
  }

}


