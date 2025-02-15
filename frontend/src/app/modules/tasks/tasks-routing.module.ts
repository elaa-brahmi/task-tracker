import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainPageComponent} from "./pages/main-page/main-page.component";
import {TaskListComponent} from "./pages/task-list/task-list.component";
import { TodoFormComponent } from './pages/todo-form/todo-form.component';
import { HomeComponent } from './components/home/home.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { LoginComponent } from 'src/app/pages/login/login.component';

const routes: Routes = [
  {
    path:'task-management',
    component:MainPageComponent,
   children:[

   ]
  }

  , { path: 'login', component: LoginComponent }
   // Add the login route
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TasksRoutingModule { }
