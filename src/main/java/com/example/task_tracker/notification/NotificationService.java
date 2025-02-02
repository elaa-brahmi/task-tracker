package com.example.task_tracker.notification;

import com.example.task_tracker.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//The NotificationService sends the generated notifications directly to the front-end
// clients using the SimpMessagingTemplate provided by Spring.

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRepository notificationRepository;
    public void sendNotification(Notification notification,String userId) {
        log.info("Sending notification to {} user with payload {} " , userId, notification);
        messagingTemplate.convertAndSendToUser(
                userId,
                "/notification",
                notification
        );

    }
    public List<NotificationResponse> getNotificationsByUserId(Authentication connectedUser) {
        User user=(User)connectedUser;
        List<Notification> list=notificationRepository.getNotificationsByAssignee(user);
        return list.stream()
                .map(notification -> NotificationResponse.builder()
                        .id(notification.getId())
                        .message(notification.getMessage())
                        .createdAt(notification.getCreatedAt())
                        .status(notification.getReadStatus())
                        .build()
                ).collect(Collectors.toList());


    }
    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }
}
