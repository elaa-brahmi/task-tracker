package com.example.task_tracker.user;

import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public static User toUser(UserRequest request) {
        return User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
    }

}
