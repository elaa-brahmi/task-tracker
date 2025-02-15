
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { TaskService } from "../../../../services/services/task.service";
import { Router } from "@angular/router";
import { PageResponseTaskResponse, TaskResponse } from 'src/app/services/models';
import { HttpResponse } from '@angular/common/http';
import { forkJoin } from 'rxjs';
@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {
  @ViewChild('input') input: ElementRef | undefined;
  filters: string[] = ['status', 'priority', 'duedate', 'category', 'keyword'];
  public viewList: boolean = true;
  public page = 0;
  public size = 2;
  public task:TaskResponse={};
  public progress:number=0;
  public finishedTasks:number=0;
  public totalTasks:number=0;
  public selectedFilter: string = '';
  taskResponse: PageResponseTaskResponse = { content: [], totalPages: 0 };

  constructor(private taskService: TaskService, private router: Router) {}

  ngOnInit(): void {
    console.log('TaskListComponent initialized');
    this.getTaskList();
    this.updateProgress();
    console.log(this.progress);
  }

  onDeleteTask(id: number): void {
    this.taskResponse.content = this.taskResponse.content?.filter(task => task.id !== id);
    this.viewList = !!(this.taskResponse.content && this.taskResponse.content.length > 0);

  }
  updateProgress() {
    forkJoin({
      finishedTasks: this.taskService.nbTasksFinished$Response(),
      totalTasks: this.taskService.nbTasks$Response()
    }).subscribe({
      next: ({ finishedTasks, totalTasks }) => {
        this.finishedTasks = finishedTasks.body || 0;
        this.totalTasks = totalTasks.body || 0;
        console.log('Finished tasks:', this.finishedTasks);
        console.log('Total tasks:', this.totalTasks);

        if (this.totalTasks > 0) {
          this.progress = (this.finishedTasks / this.totalTasks) * 100;
        } else {
          this.progress = 0;
        }
        console.log('Progress:', this.progress);
      },
      error: (err) => {
        console.error('Error getting tasks:', err);
        this.progress = 0;
      }
    });
  }

  private getTaskList() {
    this.taskService.findAllTasksByUserId$Response({
      page: this.page,
      size: this.size
    }).subscribe({
      next: (tasks: HttpResponse<PageResponseTaskResponse>) => {

    this.taskResponse = tasks.body || {};
    if (this.taskResponse.content && this.taskResponse.content.length > 0) {
      this.viewList = true;
    } else {
      this.taskResponse.content = []; // No tasks available
      this.viewList = false;
    }

      },
      error: (err) => {
        console.error('Error getting tasks:', err);
        this.viewList = false;
      }
    });
  }

  onFilterChange(event: any) {
    this.selectedFilter = event.value;
    if (this.input) {
      this.applyFilter(this.input.nativeElement.value);
    }
  }

  onInput(value: string): void {
    this.applyFilter(value);
  }

  applyFilter(value: string) {
    if (!value) {
      this.getTaskList();
      return;
    }

    switch (this.selectedFilter) {
      case 'status':
        this.taskService.findTasksByStatus$Response({ status: value }).subscribe({
          next: (tasks:HttpResponse<PageResponseTaskResponse>) => {
            this.taskResponse = tasks.body || {};
            console.log('Tasks fetched taskResponse:', this.taskResponse);
            console.log('Tasks fetched taskResponse.content:', this.taskResponse.content);
            this.viewList = !!(this.taskResponse.content && this.taskResponse.content.length > 0);

          },
          error: (err) => {console.error('Error getting tasks:', err);
            this.taskResponse = { content: [], totalPages: 0 };
            console.log('Tasks fetched taskResponse:', this.taskResponse.content);
          }
        });
        break;
      case 'priority':
        this.taskService.findTasksByPriority$Response({ priority: value }).subscribe({
          next: (tasks:HttpResponse<PageResponseTaskResponse>) => {
            this.taskResponse = tasks.body || {};
            this.viewList = !!(this.taskResponse.content && this.taskResponse.content.length > 0);
          },
          error: (err) => {
            console.error('Error getting tasks:', err);
            this.taskResponse = { content: [], totalPages: 0 };
            console.log('Tasks fetched taskResponse:', this.taskResponse.content);
          }
        });
        break;
      case 'duedate':
        this.taskService.findTasksByDueDate$Response({ DueDate: value }).subscribe({
          next: (tasks:HttpResponse<PageResponseTaskResponse>) => {
            this.taskResponse = tasks.body || {};
            this.viewList = !!(this.taskResponse.content && this.taskResponse.content.length > 0);
          },
          error: (err) =>{
            console.error('Error getting tasks:', err);
            this.taskResponse = { content: [], totalPages: 0 };


          }
        });
        break;
      case 'category':
        this.taskService.findTasksBycategory$Response({ category: value }).subscribe({
          next: (tasks:HttpResponse<PageResponseTaskResponse>) => {
            this.taskResponse = tasks.body || {};
            this.viewList = !!(this.taskResponse.content && this.taskResponse.content.length > 0);
          },
          error: (err) => {console.error('Error getting tasks:', err);
          this.taskResponse = { content: [], totalPages: 0 };
          }
        });
        break;
      case 'keyword':
        this.taskService.findTasksByKeyword$Response({ keyword: value }).subscribe({
          next: (tasks:HttpResponse<PageResponseTaskResponse>) => {
            this.taskResponse = tasks.body || {};
            this.viewList = !!(this.taskResponse.content && this.taskResponse.content.length > 0);
          },
          error: (err) => {console.error('Error getting tasks:', err);
          this.taskResponse = { content: [], totalPages: 0 };
          }
        });
        break;
      default:
        this.getTaskList();
        break;
    }
  }


  goToFirstPage() {
    this.page = 0;
    this.getTaskList();
  }

  goToPreviousPage() {
    this.page--;
    this.getTaskList();
  }

  goToNextPage() {
    this.page++;
    this.getTaskList();
  }

  goToLastPage() {
    this.page = (this.taskResponse.totalPages || 1) - 1;
    this.getTaskList();
  }

  goToPage(index: number) {
    this.page = index;
    this.getTaskList();
  }


}
