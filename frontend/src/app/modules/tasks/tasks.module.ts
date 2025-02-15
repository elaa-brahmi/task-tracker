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
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { ToastrModule } from 'ngx-toastr';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatIconModule} from '@angular/material/icon';
import { ProfileComponent } from './pages/profile/profile.component';
import { NotifComponent } from './pages/notif/notif.component';
@NgModule({
  declarations: [
    MainPageComponent,
    HomeComponent,
    TaskListComponent,
    TodoFormComponent,
    TodoComponent,
    ProfileComponent,
    NotifComponent
  ],
  imports: [
    CommonModule,
    MatButtonModule,
    MatInputModule,
    MatIconModule,
    MatProgressBarModule,
    MatFormFieldModule,
    MatSelectModule,
    FormsModule,
    ToastrModule,
    TasksRoutingModule,
    MatDatepickerModule,
    MatNativeDateModule
  ]
})
export class TasksModule { }
