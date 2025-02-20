package com.example.task_tracker.task;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResponse {
    private Integer id;
    private String title;
    private String description;
    private String category;
    private Status status;
    private Integer assignee; // not the object just the full name of the user
    private LocalDate Duedate;
    private Importance importance;


}
