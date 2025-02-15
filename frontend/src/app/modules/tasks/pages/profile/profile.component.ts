import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { UserRequest, UserResponse } from 'src/app/services/models';
import { UserControllerService } from 'src/app/services/services';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  constructor( private UserService:UserControllerService,
    private toastr:ToastrService) { }
  public ngOnInit(): void {
    if(!this.updatedUser.email && !this.updatedUser.firstName && !this.updatedUser.lastName){
      this.update = true;
    }
    this.getUser();
  }
  public updatedUser: UserRequest = {};
  public user:UserResponse = {}
  public update= false;

  public getUser(){
    this.UserService.getUserById$Response().subscribe({
      next:(response)=>{
        console.log('response:', response);
        this.user = response.body;
      },
      error:(err)=>{
        console.error('Error:', err);
      }
    });
  }
  public onSubmit(){
    this.UserService.updateUser$Response({body:this.user}).subscribe({
      next:(response)=>{
        console.log('response:', response);
        this.toastr.success('User updated successfully');

      },
      error:(err)=>{
        console.error('Error:', err);
      }
    });
  }


}
