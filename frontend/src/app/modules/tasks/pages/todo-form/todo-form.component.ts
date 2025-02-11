import { Component, OnInit } from '@angular/core';
import { TaskRequest } from 'src/app/services/models';

@Component({
  selector: 'app-todo-form',
  templateUrl: './todo-form.component.html',
  styleUrls: ['./todo-form.component.css']
})
export class TodoFormComponent implements OnInit{
  task = {
    title: '',
    description: '',
    dueDate: '',
    priority: ''
  };
constructor() { }
ngOnInit(): void {

  }
  onSubmit(){
    console.log('Task submitted:', this.task);
  }

}
