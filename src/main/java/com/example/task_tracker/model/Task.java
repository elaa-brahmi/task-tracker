package com.example.task_tracker.model;

import com.example.task_tracker.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
@Entity
@Table(name="tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_task")
    private Integer idTask;

    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name="category")
    private String category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Foreign key column in task table
    @JsonBackReference
    //@JsonBackReference: Indicates that this is the back part of the relationship (e.g., Task -> User).
    private User user;
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task(){}


    public Task(User user, String category, TaskStatus status, String description, String title) {
        this.user = user;
        this.category = category;
        this.status = status;
        this.description = description;
        this.title = title;
    }

    public Integer getIdTask() {
        return idTask;
    }

    public void setIdTask(Integer idTask) {
        this.idTask = idTask;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
