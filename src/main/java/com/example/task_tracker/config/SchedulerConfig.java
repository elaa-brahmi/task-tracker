package com.example.task_tracker.config;

import com.example.task_tracker.notification.NotifStatus;
import com.example.task_tracker.notification.Notification;
import com.example.task_tracker.notification.NotificationService;
import com.example.task_tracker.task.Task;
import com.example.task_tracker.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;
//The SchedulerConfig regularly checks tasks with due dates approaching
// (like one day before the due date) and generates notifications.

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {

    private final TaskService taskService;
    private final NotificationService notificationService;

    @Scheduled(cron = "0 0 12 * * *", zone = "Africa/Tunis") // Runs at 12:00 PM Tunisia time
    public void sendTaskReminders() {

        List<Task> tasksDueTomorrow = taskService.findTasksDueTomorrow();

        for (Task task : tasksDueTomorrow) {
            String message = "Reminder: Task '" + task.getTitle() + "' is due tomorrow!";

            Notification notification = Notification.builder()
                    .message(message)
                    .createdAt(LocalDateTime.now())
                    .assignee(task.getAssignee())
                    .task(task)
                    .readStatus(NotifStatus.UNREAD)
                    .build();

            // Save notification in the database (if required)
            notificationService.saveNotification(notification);

            // Send real-time notification to the user
            String userId = task.getAssignee().getfullName(); // Assuming username as userId
            notificationService.sendNotification(notification, userId);
        }
    }
}

