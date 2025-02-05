package com.example.task_tracker.task;

import ch.qos.logback.classic.Logger;
import com.example.task_tracker.common.PageResponse;
import com.example.task_tracker.exception.TaskNotFound;
import com.example.task_tracker.exception.UnauthorizedAccessException;
import com.example.task_tracker.notification.NotificationService;
import com.example.task_tracker.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
//import org.hibernate.session.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j

@RequiredArgsConstructor
public class TaskService {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final NotificationService notificationService;


    public List<Task> findTasksDueTomorrow(){
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        return taskRepository.findTasksByDueDateBetween(today, tomorrow);
    }

    public Integer save(TaskRequest request, Authentication connectedUser) {
        User user=((User) connectedUser.getPrincipal());
        Task task=taskMapper.toTask(request);
        task.setIdassignee(user.getId());
        task.setCreatedAt(LocalDateTime.now());

        return  taskRepository.save(task).getId();
    }

    public TaskResponse findById(Integer taskId) {
        return taskRepository.findById(taskId)
                .map(taskMapper::toTaskResponse)
        .orElseThrow(()->new EntityNotFoundException("task with id "+taskId+" is not found"));
    }

    public PageResponse<TaskResponse> findTasksByUser(int page, int size, Authentication connectedUser) {
        User user=((User) connectedUser.getPrincipal());
        Pageable pageable= PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Task> tasks=taskRepository.findTasksByAssigneeId(user.getId(),pageable);
        List<TaskResponse> taskResponse=tasks.stream()
                .map(taskMapper::toTaskResponse)
                .toList();
        return new PageResponse<>(
                taskResponse,
                tasks.getNumber(),
                tasks.getSize(),
                tasks.getTotalElements(),
                tasks.getTotalPages(),
                tasks.isFirst(),
                tasks.isLast()

        );
    }


    public PageResponse<TaskResponse> findTasksByStatus(int page, int size, Authentication connectedUser, String status) {
        User user=((User) connectedUser.getPrincipal());
        Pageable pageable= PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Task> tasks=taskRepository.findTasksByStatus(user.getId(),pageable,Status.valueOf(status.toUpperCase()));
        if(!tasks.hasContent()){
            throw new TaskNotFound("tasks with status "+status+" not found");
        }

        List<TaskResponse> taskResponse=tasks.stream()
                .map(taskMapper::toTaskResponse)
                .toList();
        return new PageResponse<>(
                taskResponse,
                tasks.getNumber(),
                tasks.getSize(),
                tasks.getTotalElements(),
                tasks.getTotalPages(),
                tasks.isFirst(),
                tasks.isLast()
        );
    }


    public PageResponse<TaskResponse> findTasksByPriority(int page, int size, Authentication connectedUser, String priority) {
        User user=((User) connectedUser.getPrincipal());
        Pageable pageable= PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Task> tasks=taskRepository.findTasksByPriority(user.getId(),pageable,Importance.valueOf(priority.toUpperCase()));
        if(!tasks.hasContent()){
            throw new TaskNotFound("tasks with priority "+priority+" not found");
        }
        List<TaskResponse> taskResponse=tasks.stream()
                .map(taskMapper::toTaskResponse)
                .toList();
        return new PageResponse<>(
                taskResponse,
                tasks.getNumber(),
                tasks.getSize(),
                tasks.getTotalElements(),
                tasks.getTotalPages(),
                tasks.isFirst(),
                tasks.isLast()
        );
    }

    public PageResponse<TaskResponse> findTasksByDueDate(int page, int size, Authentication connectedUser, String dueDate) {
        User user=((User) connectedUser.getPrincipal());
        Pageable pageable= PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Task> tasks=taskRepository.findTasksByDueDate(user.getId(),pageable, LocalDate.parse(dueDate));
        if(!tasks.hasContent()){
            throw new TaskNotFound("tasks not found having due Date "+dueDate);
        }
        List<TaskResponse> taskResponse=tasks.stream()
                .map(taskMapper::toTaskResponse)
                .toList();
        return new PageResponse<>(
                taskResponse,
                tasks.getNumber(),
                tasks.getSize(),
                tasks.getTotalElements(),
                tasks.getTotalPages(),
                tasks.isFirst(),
                tasks.isLast()
        );
    }

    public void updateTaskById(Integer idTask, TaskRequest request, Authentication connectedUser) {
        User user=((User) connectedUser.getPrincipal());
        Task task=taskMapper.toTask(request);
        Task oldTask=taskRepository.findById(idTask).get();
        oldTask.setUpdatedAt(LocalDateTime.now());
        oldTask.setTitle(task.getTitle());
        oldTask.setDescription(task.getDescription());
        oldTask.setImportance(task.getImportance());
        oldTask.setStatus(task.getStatus());
        taskRepository.save(oldTask);

    }

    public void deleteTaskById(Integer idTask, Authentication connectedUser) {
        User user=((User) connectedUser.getPrincipal());
        Task task=taskRepository.findById(idTask).get();
        taskRepository.delete(task);
    }
    public void updateStatusOfATask(Integer idTask, TaskStatusRequest statusRequest, Authentication connectedUser) throws UnauthorizedAccessException {

            User user=((User) connectedUser.getPrincipal());
            Task task=taskRepository.findById(idTask).orElseThrow(()->new TaskNotFound("task not found"));
            if(task.getIdassignee().equals(user.getId())){
                logger.info("ok");
                task.setStatus(Status.valueOf(statusRequest.getStatus().trim().toUpperCase().replace(" ", "_")));
                logger.info(task.getStatus().name());
                taskRepository.save(task);

            }

    }

    public PageResponse<TaskResponse> searchByKeyword(int page, int size, Authentication connectedUser, String keyword) {
        logger.info(keyword);
        User user=((User) connectedUser.getPrincipal());
        Pageable pageable= PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Task> tasks=taskRepository.SearchByKeyword(user.getId(),pageable,keyword);
        if(!tasks.hasContent()){
            logger.info("No tasks found for the keyword: '{}'. Returning all tasks for user ID: {}", keyword, user.getId());
            return new PageResponse<>( new ArrayList<TaskResponse>(),
                    tasks.getNumber(),
                    tasks.getSize(),
                    tasks.getTotalElements(),
                    tasks.getTotalPages(),
                    tasks.isFirst(),
                    tasks.isLast()
            );
        }
        List<TaskResponse> taskResponse=tasks.stream()
                .map(taskMapper::toTaskResponse)
                .toList();
        return new PageResponse<>(
                taskResponse,
                tasks.getNumber(),
                tasks.getSize(),
                tasks.getTotalElements(),
                tasks.getTotalPages(),
                tasks.isFirst(),
                tasks.isLast()
        );

    }
    public int getFinishedTasks(Authentication connectedUser, Status status) {
        User user=((User) connectedUser.getPrincipal());
         List<Task> list=taskRepository.findTasksByStatusFinished(user.getId(),Status.FINISHED);
         return list.size();

    }

    public Integer getnbTasks(Authentication connectedUser) {
        User user=((User) connectedUser.getPrincipal());
       int s =taskRepository.findTasksByUserId(user.getId());
        return s;
    }

    public PageResponse<TaskResponse> findTasksByCategory(int page, int size, Authentication connectedUser, String category) {

        User user=((User) connectedUser.getPrincipal());
        Pageable pageable= PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Task> tasks=taskRepository.findTasksByCategory(user.getId(),pageable, category);
        if(!tasks.hasContent()){
            throw new TaskNotFound("tasks not found having category "+category);
        }
        List<TaskResponse> taskResponse=tasks.stream()
                .map(taskMapper::toTaskResponse)
                .toList();
        return new PageResponse<>(
                taskResponse,
                tasks.getNumber(),
                tasks.getSize(),
                tasks.getTotalElements(),
                tasks.getTotalPages(),
                tasks.isFirst(),
                tasks.isLast()
        );
    }
}
