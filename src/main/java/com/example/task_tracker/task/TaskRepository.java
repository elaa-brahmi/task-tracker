package com.example.task_tracker.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("""
            SELECT task 
            FROM Task task
            WHERE task.assignee.id = :id
            """

    )
     Page<Task> findTasksByAssigneeId(Integer id, Pageable pageable);
    @Query("""
            SELECT task 
            FROM Task task
            WHERE task.assignee.id = :id
            AND task.status=:status
            """

    )
    Page<Task> findTasksByStatus(Integer id, Pageable pageable, Status status);
    @Query("""
            SELECT task 
            FROM Task task
            WHERE task.assignee.id = :id
            AND task.importance=:priority
            """

    )
    Page<Task> findTasksByPriority(Integer id, Pageable pageable, Importance priority);
    @Query("""
            SELECT task 
            FROM Task task
            WHERE task.assignee.id = :id
            AND task.dueDate=:parse
            """

    )
    Page<Task> findTasksByDueDate(Integer id, Pageable pageable, LocalDate parse);
    @Query("""
            SELECT t 
            FROM Task t
            WHERE t.assignee.id = :id
            AND (LOWER(t.title) LIKE %:keyword% OR LOWER(t.description) LIKE %:keyword%)
            """

    )
    Page<Task> SearchByKeyword(Integer id, Pageable pageable, String keyword);

    List<Task> findTasksByDueDateBetween(LocalDate dueDateAfter, LocalDate dueDateBefore);
}
