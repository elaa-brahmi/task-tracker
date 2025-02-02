package com.example.task_tracker.notification;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
@Tag(name="notification")
public class NotificationController {
    private final NotificationService notificationService;
    @GetMapping()
    public ResponseEntity<List<NotificationResponse>> getAllNotificationsByUser(
            Authentication connectedUser
    )
    {
        return ResponseEntity.ok( notificationService.getNotificationsByUserId(connectedUser));
    }

}
