package com.example.task_tracker.task;

import com.example.task_tracker.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
// @Getter generates getter methods for all fields of the class
@Getter
//generate setter methods of all fields
@Setter
//Implements the builder pattern for the class,
// allowing you to create instances of the class in a readable, step-by-step manner.
@Builder
//Generates a constructor with parameters for all fields of the class.
@AllArgsConstructor
// generates a default constructor
@NoArgsConstructor
@Entity
@Table(name="task")
@EntityListeners(AuditingEntityListener.class)
public class Task {
    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Importance importance;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    @JsonIgnore
    // to ignore infinite loops
    private User assignee;
}
