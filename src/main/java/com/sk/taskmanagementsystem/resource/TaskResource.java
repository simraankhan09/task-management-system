package com.sk.taskmanagementsystem.resource;

import com.sk.taskmanagementsystem.common.ResponseMessage;
import com.sk.taskmanagementsystem.entity.Task;
import com.sk.taskmanagementsystem.model.TaskAddResource;
import com.sk.taskmanagementsystem.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@CrossOrigin
public class TaskResource {

    @Autowired
    private TaskService taskService;

    @PostMapping("/")
    @CrossOrigin
    public ResponseEntity<ResponseMessage<Task>> createNewTask(@Valid @RequestBody TaskAddResource taskAddResource){
        return this.taskService.createNewTask(taskAddResource);
    }

    @GetMapping("/all")
    @CrossOrigin
    public ResponseEntity<List<Task>> getAllTask(){
        return this.taskService.getAllTask();
    }

    @GetMapping("/task-id/{taskId}")
    @CrossOrigin
    public ResponseEntity<Object> getById(@PathVariable("taskId") Long taskId){
        return this.taskService.getById(taskId);
    }

    @PutMapping("/update-task")
    @CrossOrigin
    public ResponseEntity<Task> updateTask(@Valid @RequestBody Task task){
        return this.taskService.updateTask(task);
    }

    @DeleteMapping("delete-task/{taskId}")
    @CrossOrigin
    public ResponseEntity<Object> deleteTask(@PathVariable("taskId") Long taskId){
        return this.taskService.deleteTask(taskId);
    }

    @PutMapping("/update-task-status/task-id/{taskId}/task-status/{taskStatus}")
    @CrossOrigin
    public ResponseEntity<Object> updateTaskStatusById(@PathVariable("taskId") Long taskId, @PathVariable("taskStatus") String taskStatus){
        return this.taskService.updateTaskStatusById(taskId, taskStatus);
    }

}
