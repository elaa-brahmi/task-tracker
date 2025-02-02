package com.example.task_tracker.task;

import com.example.task_tracker.common.PageResponse;
import com.example.task_tracker.exception.UnauthorizedAccessException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tasks")
@RequiredArgsConstructor
@Tag(name="task")
public class TaskController {
    private final TaskService taskService;
   @PostMapping()
    public ResponseEntity<Integer> saveTask(@Valid @RequestBody TaskRequest request,
                                            Authentication connectedUser
                                            ) {
       return ResponseEntity.ok(taskService.save(request,connectedUser));

   }
   @GetMapping("/id/{task-id}")
    public ResponseEntity<TaskResponse> findTaskById(
            @PathVariable("task-id") Integer taskId) {
       return ResponseEntity.ok(taskService.findById(taskId));


   }
   @GetMapping()
   //implement the paging functionality(if we had 1000 of tasks the
   //reponse will be heavy so we need to implement paging
    public ResponseEntity<PageResponse<TaskResponse>> findAllTasksByUserId(
            @RequestParam(name="page",defaultValue="0",required=false) int page,
            @RequestParam(name="size",defaultValue="6",required=false) int size,
            Authentication connectedUser
   ) {
       return ResponseEntity.ok(taskService.findTasksByUser(page,size,connectedUser));
   }
   @GetMapping("/status/{status}")
    public ResponseEntity<PageResponse<TaskResponse>> findTasksByStatus(
           @RequestParam(name="page",defaultValue="0",required=false) int page,
           @RequestParam(name="size",defaultValue="6",required=false) int size,
           Authentication connectedUser,
           @PathVariable("status") String status

   ){
       return ResponseEntity.ok(taskService.findTasksByStatus(page,size,connectedUser,status));

   }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<PageResponse<TaskResponse>> findTasksByPriority(
            @RequestParam(name="page",defaultValue="0",required=false) int page,
            @RequestParam(name="size",defaultValue="6",required=false) int size,
            Authentication connectedUser,
            @PathVariable("priority") String priority

    ){
        return ResponseEntity.ok(taskService.findTasksByPriority(page,size,connectedUser,priority));

    }

    @GetMapping("/DueDate/{DueDate}")
    public ResponseEntity<PageResponse<TaskResponse>> findTasksByDueDate(
            @RequestParam(name="page",defaultValue="0",required=false) int page,
            @RequestParam(name="size",defaultValue="6",required=false) int size,
            Authentication connectedUser,
            @PathVariable("DueDate") String DueDate

    ){
        return ResponseEntity.ok(taskService.findTasksByDueDate(page,size,connectedUser,DueDate));

    }

    @PutMapping("/{idTask}")
    public ResponseEntity<Void> updateTask(
            @PathVariable Integer idTask,
            @Valid @RequestBody TaskRequest request,
            Authentication connectedUser
    )
    {
        taskService.updateTaskById(idTask,request,connectedUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{idTask}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Integer idTask,
            Authentication connectedUser
    )
    {
        taskService.deleteTaskById(idTask,connectedUser);
        return ResponseEntity.ok().build();


}

    @Transactional
    @PatchMapping("/status/{idTask}")
    public ResponseEntity<Void> updateTaskStatus(
            @PathVariable("idTask") Integer idTask,
            Authentication connectedUser,
            @RequestBody String statusRequest
    ) throws UnauthorizedAccessException {
        Object principal = connectedUser.getPrincipal();
        System.out.println("Principal class: " + principal.getClass());
       taskService.updateStatusOfATask(idTask,statusRequest,connectedUser);
       return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<TaskResponse>> findTasksByKeyword(
            Authentication connectedUser,
            @RequestParam(name="keyword", required=true) String keyword,
            @RequestParam(name="page",defaultValue="0",required=false) int page,
            @RequestParam(name="size",defaultValue="6",required=false) int size

            ){
        return ResponseEntity.ok(taskService.searchByKeyword(page,size,connectedUser,keyword));
    }
}
