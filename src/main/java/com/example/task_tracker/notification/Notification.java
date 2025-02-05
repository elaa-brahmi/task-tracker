package com.example.task_tracker.notification;

import com.example.task_tracker.task.Task;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@ToString(exclude = "task")
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
    private Integer assignee;

    @OneToOne
    @JoinColumn(name = "task_id")
    @JsonBackReference
    @JsonIgnore
    private Task task;

    @Enumerated(EnumType.STRING)
    private NotifStatus readStatus;
}
