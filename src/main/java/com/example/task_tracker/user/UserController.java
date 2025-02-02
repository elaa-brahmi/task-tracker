package com.example.task_tracker.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PutMapping()
    public ResponseEntity<Void> updateUser(@RequestBody UserRequest user
    , Authentication connectedUser) {
        userService.updateUser(connectedUser,user);
        return ResponseEntity.ok().build();

    }


}
