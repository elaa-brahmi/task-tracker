import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // Import FormsModule
import { TasksRoutingModule } from './tasks-routing.module';
import { MainPageComponent } from './pages/main-page/main-page.component';
import { HomeComponent } from './components/home/home.component';
import { TaskListComponent } from './pages/task-list/task-list.component';
import { TodoFormComponent } from './pages/todo-form/todo-form.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { TodoComponent } from './components/todo/todo.component';
import {MatSelectModule} from '@angular/material/select';

@NgModule({
  declarations: [
    MainPageComponent,
    HomeComponent,
    TaskListComponent,
    TodoFormComponent,
    TodoComponent
  ],
  imports: [
    CommonModule,

    MatSelectModule,
    FormsModule,
    TasksRoutingModule,
    MatDatepickerModule,
    MatNativeDateModule
  ]
})
export class TasksModule { }
