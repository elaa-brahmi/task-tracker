package com.example.task_tracker.repository;

import com.example.task_tracker.model.Task;
import com.example.task_tracker.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findTasksByUser_IdUser(Integer userIdUser);

    List<Task> findAllByCategory(String category);

    List<Task> findAllByStatus(TaskStatus status);;
}
