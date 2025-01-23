//package com.example.task_tracker.controller;
//
//import com.example.task_tracker.model.Task;
//import com.example.task_tracker.model.TaskStatus;
//import com.example.task_tracker.service.TaskService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/tasks")
//public class TaskController {
//    @Autowired
//    private TaskService taskService;
//    @GetMapping("/tasks-list")
//    public List<Task> getAllTasks() {
//        return taskService.getAllTasks();
//    }
//    @GetMapping("/{idTask}")
//    public Task getTaskById(@PathVariable int idTask) {
//        return taskService.getTaskById(idTask);
//    }
//    @GetMapping("/tasksByIdUser/{idUser}")
//    public List<Task> getTasksByUser(@PathVariable int idUser) {
//        return taskService.getAllTasksByUserId(idUser);
//    }
//    @GetMapping("/tasksByCategory/{category}")
//    public List<Task> getTasksByCategory(@PathVariable String category) {
//        return taskService.getTasksByCategory(category);
//    }
//    @GetMapping("/tasksByStatus/{status}")
//    public List<Task> getTasksByStatus(@PathVariable String status) {
//        return taskService.getTasksByStatus(TaskStatus.valueOf(status));
//    }
//    @PostMapping("/add-task")
//    public void addTask(@RequestBody Task task) {
//        taskService.addTask(task);
//    }
//    @PutMapping("/update-task")
//    public void updateTask(@RequestBody Task task) {
//        taskService.updateTask(task);
//    }
//    @DeleteMapping("/DeleteTask/{id}")
//    public void deleteTask(@PathVariable int id) {
//        taskService.deleteTaskById(id);
//    }
//    @DeleteMapping("/DeleteTasks/{idUser}")
//    public void deleteTasksByUser(@PathVariable int idUser) {
//        taskService.deleteAllTasksByUserId(idUser);
//    }
//
//}
