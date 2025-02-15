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
  @Output() taskUpdated=new EventEmitter<number>();

@Output() taskStatusChanged=new EventEmitter<TaskStatusRequest>();
  completed:boolean=false;
  statusList:string[] = ['PENDING','IN_PROGRESS','FINISHED'];
  selectedValue:string='';
  message:string='';

  constructor( public TaskService:TaskService,public router:Router,
    private toastr: ToastrService
  ) { }

  ngOnInit(): void {
    console.log('Task received in app-todo:', this.task);
    this.selectedValue = this.task.status as string;
  }
  deleteTodo(){
    const params = {
      idTask: this.task.id as number
    }
    this.TaskService.deleteTask(params).subscribe({
      next:()=>{
        console.log('Task deleted');
        this.toastr.error(`Todo ${this.task.id} Deleted!`, 'Deleted Successfuly');
        this.taskDeleted.emit(this.task.id as number);// Emit an event when the task is deleted

      },
      error:(error) => {
        this.toastr.error('Error deleting task');
        console.error('Error deleting task:', error);
      }
  });
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
        this.taskStatusChanged.emit();

        console.log('Task status updated to ', this.task.status);
        this.toastr.success(`Task status updated to ${this.task.status}`,'Updated Successfuly');

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
        this.taskStatusChanged.emit();
          console.log('Task status updated to finished','FINISHED');
          this.toastr.success('Task status updated to finished');


          setTimeout(() => {
            if(confirm(' this task is finished . Do you want to delete this task?')){
              this.TaskService.deleteTask$Response({idTask: this.task.id as number}).subscribe({
                next:()=>{
                  this.toastr.error('Task is deleted','Deleted Successfuly');
                  this.taskDeleted.emit(this.task.id as number);// Emit an event when the task is deleted
                  console.log('Task deleted');
                },
                error:(error) => {
                  console.error('Error deleting task:', error);
                }
              });
            }
        },1000);
      },
      error:(error) => {
        console.error('Error updating task status:', error);
        this.toastr.error('Error updating task status');}
      });
  }
  toggleClass(){
      return { 'list-group-item-success': this.task.status === 'FINISHED',
         'border-primary': this.task.status === 'FINISHED' };
  }




}
