import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent  {
  constructor(private router:Router) { }

  logout() {
    console.log("logout clicked");
    localStorage.removeItem('token');
    // Redirect to the login page


  }
  public profile= false;
  public addTask= false;
  public list= true;
  public notif=false;

}
