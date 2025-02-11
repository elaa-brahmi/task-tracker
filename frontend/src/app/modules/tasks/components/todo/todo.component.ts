import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { TasksModule } from '../../tasks.module';
import { TaskService } from 'src/app/services/services';
import { Router } from '@angular/router';
import {  TaskResponse, TaskStatusRequest } from 'src/app/services/models';
import { ToastrService } from 'ngx-toastr';
@Component({
  selector: 'app-todo',
  templateUrl: './todo.component.html',
  styleUrls: ['./todo.component.css']
})
// this is a component for a task and will be used later in task-list component
export class TodoComponent implements OnInit {
  @Input() task: TaskResponse={}; //a task has attributes
  @Output() taskDeleted=new EventEmitter<number>();
  completed:boolean=false;
   statusList:string[] = ['PENDING','IN_PROGRESS'];
   selectedValue:string='';
   message:string='';

  constructor( public TaskService:TaskService,public router:Router,
    private toastr: ToastrService
  ) { }

  ngOnInit(): void {
    this.selectedValue = this.task.status as string;
  }

  onChange():void{
    const params={
      idTask: this.task.id as number,
      body: {
        status: this.task.status
      }
    };
    this.TaskService.updateTaskStatus$Response(params).subscribe({
      next:()=>{
        console.log('Task status updated to ', this.task.status);
        this.toastr.success('Task status updated to '+ this.task.status);

      },
      error:(error) => {
        console.error('Error updating task status:', error);
        this.toastr.error('Error updating task status');
      }

    });
  }
  onChangeFinished(){

    const params={
      idTask: this.task.id as number,
      body: {
        status: 'FINISHED'
      }
    };
    this.TaskService.updateTaskStatus$Response(params).subscribe({
      next:()=>{

          console.log('Task status updated to finished');

          this.TaskService.deleteTask$Response({idTask: this.task.id as number}).subscribe({
            next:()=>{
              this.toastr.success('Task is finished');
              this.taskDeleted.emit(this.task.id as number);// Emit an event when the task is deleted
              console.log('Task deleted');

            },
            error:(error) => {
              console.error('Error deleting task:', error);
            }
          });
        }
      });




  }
  toggleClass(){
    return {
      'list-group-item-success': this.task.status === 'FINISHED',
      'border-primary': this.task.status === 'IN_PROGRESS'
    };
  }
  deleteTodo(){
    const params = {
      idTask: this.task.id as number
    }
    this.TaskService.deleteTask(params).subscribe({
      next:()=>{
        console.log('Task deleted');
        this.toastr.success('Task deleted');
        this.taskDeleted.emit(this.task.id as number);// Emit an event when the task is deleted
        this.router.navigate(['/tasks']);
      },
      error:(error) => {
        this.toastr.error('Error deleting task');
        console.error('Error deleting task:', error);
      }
  });
  }



}
