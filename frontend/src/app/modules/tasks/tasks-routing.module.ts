import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainPageComponent} from "./pages/main-page/main-page.component";
import {TaskListComponent} from "./pages/task-list/task-list.component";
import { TodoFormComponent } from './pages/todo-form/todo-form.component';

const routes: Routes = [
  {
    path:'',
    component:MainPageComponent,
   children:[

     {
      path:'todo-form',
      component:TodoFormComponent
     }

   ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TasksRoutingModule { }
