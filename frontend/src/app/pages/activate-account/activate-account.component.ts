import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";

@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrls: ['./activate-account.component.css']
})
export class ActivateAccountComponent {
  message:string='';
  isOkay=true;
  submitted=false;
  constructor(
    private router:Router,
    private authService:AuthenticationService
  ){}

  onCodeCompleted(token: string) {
    this.confirmAccount(token);

  }

  redirectToLogin() {
    this.router.navigate(['login']);
  }

  private confirmAccount(token: string) {
    this.authService.confirm({
      token
    }).subscribe({
      next:()=>{
        this.message="your account has been successfully activated .\n now you can proceed to login";
        this.submitted=true;
        this.isOkay=true;
      },error:() => {
      this.message="token has been expired or invalid";
      this.submitted=true;
        this.isOkay=false;

    }
    });

  }
}
