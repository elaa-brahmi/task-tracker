import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { TaskRequest } from 'src/app/services/models';
import { TaskService } from 'src/app/services/services';

@Component({
  selector: 'app-todo-form',
  templateUrl: './todo-form.component.html',
  styleUrls: ['./todo-form.component.css']
})
export class TodoFormComponent implements OnInit{
  public statuses = ['PENDING', 'IN_PROGRESS', 'FINISHED'];
  public priorities = ['LOW', 'MEDIUM', 'HIGH'];
  task:TaskRequest = {

    category:'',
    description: '',
    status:'',
    dueDate: '',
    title:'',
    importance: 'LOW'
  };
  invalid:boolean=false;
constructor(private taskService:TaskService,private router:Router,
  private ToastService:ToastrService
) { }
ngOnInit(): void {
  if(this.task.category=='' || this.task.importance==null || this.task.description=='' || this.task.status=='' || this.task.dueDate=='' || this.task.title==''){
    this.invalid=true;

  }}
  onSubmit(){
    console.log('Task submitted:', this.task);
    const param={
      body:this.task
    }
    this.taskService.saveTask$Response(param).subscribe({
      next:(response)=>{
        console.log('response:', response);
        this.ToastService.success('Task saved successfully');
        this.router.navigate(['/task-list']);
      },
      error:(err)=>{
        console.error('Error:', err);
        this.ToastService.error('Error saving task');
      }
    });
  }

}
