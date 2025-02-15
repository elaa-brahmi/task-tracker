package com.example.task_tracker.notification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private LocalDate createdAt;
    private NotifStatus status;
}
