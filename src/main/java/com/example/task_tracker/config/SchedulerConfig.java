package com.example.task_tracker.config;

import com.example.task_tracker.notification.NotifStatus;
import com.example.task_tracker.notification.Notification;
import com.example.task_tracker.notification.NotificationService;
import com.example.task_tracker.task.Task;
import com.example.task_tracker.task.TaskRepository;
import com.example.task_tracker.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
//The SchedulerConfig regularly checks tasks with due dates approaching
// (like one day before the due date) and generates notifications.

@Component
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {

    private final TaskService taskService;
    private final NotificationService notificationService;
    private final TaskRepository taskRepository;
//@Scheduled(fixedDelay = 3000)
@Scheduled(cron = "0 49 21 * * *", zone = "Africa/Tunis")
    public void sendTaskReminders() {
        System.out.println("Sending reminders");

        List<Task> tasksDueTomorrow = taskService.findTasksDueTomorrow();

        for (Task task : tasksDueTomorrow) {
            String message = "Reminder: Task '" + task.getTitle() + "' is due tomorrow!";

            Notification notification = Notification.builder()
                    .message(message)
                    .createdAt(LocalDateTime.now())
                   .assignee(task.getIdassignee())
                    .task(task)
                    .readStatus(NotifStatus.UNREAD)
                    .build();


            task.setNotification(notification);
            // Save notification in the database (if required)
            notificationService.saveNotification(notification);

            // Send real-time notification to the user
            Integer userId = task.getIdassignee(); // Assuming username as userId
           notificationService.sendNotification(notification, userId);
        }
    }
}

