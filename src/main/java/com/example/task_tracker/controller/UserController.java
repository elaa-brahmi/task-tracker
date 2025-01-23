package com.example.task_tracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/list-users")
//    public List<User> userList(Model model) {
//        return userService.getAllUsers();
//    }
//    @GetMapping("/{id}")
//    public User getUser(@PathVariable int id) {
//        return userService.getUser(id);
//    }
//    @PostMapping("/add")
//    public void addUser(@RequestBody User user) {
//        userService.addUser(user);
//    }
//    @PutMapping("/update")
//    public void updateUser(@RequestBody User user) {
//        userService.updateUser(user);
//    }
//    @DeleteMapping("/delete/{id}")
//    public void deleteUser(@PathVariable int id) {
//        userService.deleteUser(id);
//    }

}
