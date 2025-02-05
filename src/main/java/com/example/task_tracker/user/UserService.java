package com.example.task_tracker.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void updateUser(Authentication connectedUser,UserRequest userRequest) {
        User currentUser=((User) connectedUser.getPrincipal());
        User updatedUser=UserMapper.toUser(userRequest);
        currentUser.setEmail(updatedUser.getEmail());
        currentUser.setFirstName(updatedUser.getFirstName());
        currentUser.setLastName(updatedUser.getLastName());
        userRepository.save(currentUser);
    }

    public UserResponse getUserById(Authentication connectedUser) {
        User currentUser=((User) connectedUser.getPrincipal());
        return userMapper.toUserResponse(currentUser);


    }
}
