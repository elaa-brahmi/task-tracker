package com.example.task_tracker.notification;

import com.example.task_tracker.user.User;
import com.example.task_tracker.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//The NotificationService sends the generated notifications directly to the front-end
// clients using the SimpMessagingTemplate provided by Spring.

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    public void sendNotification(Notification notification,Integer userId) {
        log.info("Sending notification to {} user with payload {} " , userId, notification);
        User user=userRepository.findById(userId).get();
        messagingTemplate.convertAndSendToUser(
                user.getfullName(),
                "/notification",
                notification
        );

    }
    public List<NotificationResponse> getNotificationsByUserId(Authentication connectedUser) {

        User user=((User)connectedUser.getPrincipal());
        List<Notification> list=notificationRepository.getNotificationsByAssigneeId(user.getId());

        return list.stream()
                .map(notification -> NotificationResponse.builder()
                        .id(notification.getId())
                        .message(notification.getMessage())
                        .createdAt(LocalDate.parse(notification.getCreatedAt().toString().substring(0, 10)))
                        .status(notification.getReadStatus())
                        .build()
                ).collect(Collectors.toList());


    }
    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    public void markAsRead(Integer id, Authentication connectedUser) {
        User user=((User)connectedUser.getPrincipal());
        Optional<Notification> n=notificationRepository.findById(id);
        if(n.isPresent() ) {
            Notification notification=n.get();
            notification.setReadStatus(NotifStatus.READ);
            notificationRepository.save(notification);

        }

    }
}
