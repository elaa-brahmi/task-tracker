package com.example.task_tracker.notification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
    private Integer id;
    @NotEmpty
    @NotBlank
    private String message;
    @NotEmpty
    @NotBlank
    private LocalDateTime createdAt;
    private NotifStatus status;
}
