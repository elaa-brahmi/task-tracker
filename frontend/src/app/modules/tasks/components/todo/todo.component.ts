import { Component, Input, OnInit } from '@angular/core';
import { TasksModule } from '../../tasks.module';
import { TaskService } from 'src/app/services/services';
import { Router } from '@angular/router';
import {  TaskResponse, TaskStatusRequest } from 'src/app/services/models';
import { updateTaskStatus } from 'src/app/services/fn/task/update-task-status';

@Component({
  selector: 'app-todo',
  templateUrl: './todo.component.html',
  styleUrls: ['./todo.component.css']
})
// this is a component for a task and will be used later in task-list component
export class TodoComponent implements OnInit {
  @Input() task: TaskResponse={}; //a task has attributes
  completed:boolean=false;
   statusList:string[] = ['PENDING', 'FINISHED','IN_PROGRESS'];
   selectedValue:string='';

  constructor( public TaskService:TaskService,public router:Router) { }

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
        //this.task.status = this.selectedValue as 'PENDING' | 'FINISHED' | 'IN_PROGRESS'; // Update the local task status; // Update the local task status
      },
      error:(error) => {
        console.error('Error updating task status:', error);}

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
        this.router.navigate(['/tasks']);
      },
      error:(error) => {
        console.error('Error deleting task:', error);
      }
  });
  }



}
