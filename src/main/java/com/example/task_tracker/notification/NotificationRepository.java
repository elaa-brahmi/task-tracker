package com.example.task_tracker.notification;

import com.example.task_tracker.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> getNotificationsByAssignee(User assignee);
}
