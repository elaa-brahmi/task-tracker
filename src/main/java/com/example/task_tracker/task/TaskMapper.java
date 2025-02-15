package com.example.task_tracker.task;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service

public class TaskMapper {
    public Task toTask(TaskRequest request) {
        String dateOnly = request.dueDate().substring(0, 10);

        // Parse as LocalDate
        LocalDate localDate = LocalDate.parse(dateOnly);
        return Task.builder()
                .id(request.id())
                .title(request.title())
                .category(request.category())
                .description(request.description())
                .status(Status.valueOf((request.status())))
                .dueDate(localDate)
                .importance(request.importance())
                .build();
    }



    public TaskResponse toTaskResponse( Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .category(task.getCategory())
                .status(task.getStatus())
                .importance(task.getImportance())
                .assignee(task.getIdassignee())
                .Duedate(task.getDueDate())
                .build();
    }
}
