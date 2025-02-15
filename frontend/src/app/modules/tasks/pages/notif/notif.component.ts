import { HttpResponse } from '@angular/common/http';
import { NotificationResponse } from 'src/app/services/models';
import { NotificationService } from 'src/app/services/services';
import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-notif',
  templateUrl: './notif.component.html',
  styleUrls: ['./notif.component.css']
})
export class NotifComponent implements OnInit {
 public notifResponse:NotificationResponse[]=[];

  constructor(private notifService:NotificationService, private toastr: ToastrService) { }
  @ViewChild('menuIcon') menuIcon!: ElementRef;
  @ViewChild('panel') panel!: ElementRef;
  @ViewChild('menu') menu!: ElementRef;
  @ViewChild('circle1') circle1!: ElementRef;
  @ViewChild('dashTop') dashTop!: ElementRef;
  ngOnInit(): void {
    this.getNotifs();
  }
  public toggleMenu(): void {
    this.panel.nativeElement.classList.toggle('show-menu');
    this.menu.nativeElement.classList.toggle('active');
    this.circle1.nativeElement.classList.toggle('slide');
    this.dashTop.nativeElement.classList.toggle('slide-top');
  }
  markAsread(id:number){
    this.notifService.markRead$Response({id}).subscribe({
      next:(data:HttpResponse<any>)=>{
        console.log(data);
        this.getNotifs();
        this.toastr.success('Notification marked as read');
      },
      error:(err:any)=>{
        console.log(err);
      }
    });
  }
  public getNotifs() {
    this.notifService.getAllNotificationsByUser$Response().subscribe({
      next:(data:HttpResponse<Array<NotificationResponse>>)=>{
        this.notifResponse=data.body || [];
        console.log(this.notifResponse);
      },
      error:(err:any)=>{
        console.log(err);

      }

    });


  }

}
