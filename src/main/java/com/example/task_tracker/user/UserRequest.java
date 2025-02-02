package com.example.task_tracker.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {
    @NotNull(message="first name is mandatory")
    @NotEmpty(message="first name is mandatory")
    private String firstName;
    @NotNull(message="last name is mandatory")
    @NotEmpty(message="last name is mandatory")
    private String lastName;
    @NotNull(message="email is mandatory")
    @NotEmpty(message="email is mandatory")
    private String email;

}
