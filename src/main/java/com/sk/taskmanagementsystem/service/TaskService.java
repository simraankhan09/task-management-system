package com.sk.taskmanagementsystem.service;

import com.sk.taskmanagementsystem.common.ResponseMessage;
import com.sk.taskmanagementsystem.entity.Task;
import com.sk.taskmanagementsystem.model.TaskAddResource;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaskService {
    ResponseEntity<List<Task>> getAllTask();

    ResponseEntity<ResponseMessage<Task>> createNewTask(TaskAddResource taskAddResource);

    ResponseEntity<Object> getById(Long taskId);

    ResponseEntity<Task> updateTask(Task task);

    ResponseEntity<Object> deleteTask(Long taskId);

    ResponseEntity<Object> updateTaskStatusById(Long taskId, String taskStatus);
}
