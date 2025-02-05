package com.example.task_tracker.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    //List<Notification> getNotificationsByAssignee(User assignee);
    @Query("""
            SELECT n FROM Notification n
                        WHERE n.assignee =:id
            """)
    List<Notification> getNotificationsByAssigneeId(Integer id);
}
