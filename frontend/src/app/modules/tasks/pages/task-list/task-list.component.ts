import {Component, OnInit} from '@angular/core';
import {TaskService} from "../../../../services/services/task.service";
import {Router} from "@angular/router";
import {TasksModule} from "../../tasks.module";
import { PageResponseTaskResponse } from 'src/app/services/models';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit{
  public viewList:boolean=true;
  public page=0;
  public size=5;
  taskResponse:PageResponseTaskResponse={};
  constructor(private taskService:TaskService,private router:Router){

  }
  ngOnInit(): void {
    console.log('TaskListComponent initialized');
    this.getTaskList();
  }
  onDeleteTask(id:number):void{
    this.taskResponse.content = this.taskResponse.content?.filter(task=>task.id!==id);
    this.router.navigate(['/tasks']);
  }
  goToFirstPage(){
    this.page=0;
    this.getTaskList();

  }
  goToPreviousPage(){
    this.page--;
    this.getTaskList();

  }
  goToNextPage(){
    this.page++;
    this.getTaskList();
  }
  goToLastPage(){
    this.page=this.taskResponse.totalPages as number -1; // we need to cast it to number because it is probably undefined
    this.getTaskList();
  }
  goToPage(index:number){}
  private getTaskList() {
    return this.taskService.findAllTasksByUserId({
      page:this.page,
      size:this.size
    }).subscribe({
      next:(tasks:PageResponseTaskResponse)=>{
        console.log(tasks);
        this.taskResponse=tasks;
        this.viewList = !!(tasks.content && tasks.content.length > 0); // If there are tasks, show the list(exclamation mark is used to convert the value to boolean explicitly)
        console.log(this.taskResponse);

      },
      error:(err)=>{
        console.error('Error getting tasks:',err);
        this.viewList=false;
      }
    });

  }
}
