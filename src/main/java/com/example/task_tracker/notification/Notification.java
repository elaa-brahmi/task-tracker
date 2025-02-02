package com.example.task_tracker.notification;

import com.example.task_tracker.task.Task;
import com.example.task_tracker.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name="notification")
@EntityListeners(AuditingEntityListener.class)

public class Notification {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String message;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id",nullable = false)
    @JsonIgnore
    private User assignee;

    @OneToOne(optional = true)
    @JoinColumn(name="task_id")
    @JsonIgnore
    private Task task;

    @Enumerated(EnumType.STRING)
    private NotifStatus readStatus;
}
