package com.example.task_tracker.task;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service

public class TaskMapper {
    public Task toTask(TaskRequest request) {
        return Task.builder()
                .id(request.id())
                .title(request.title())
                .description(request.description())
                .status(Status.valueOf((request.status())))
                .dueDate(LocalDate.parse(request.dueDate()))
                .importance(request.importance())
                .build();
    }



    public TaskResponse toTaskResponse( Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .importance(task.getImportance())
                .assignee(task.getAssignee().getfullName())
                .Duedate(task.getDueDate())
                .build();
    }
}
