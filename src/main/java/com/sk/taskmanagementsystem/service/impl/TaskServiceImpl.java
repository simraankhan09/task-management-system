package com.sk.taskmanagementsystem.service.impl;

import com.sk.taskmanagementsystem.common.ResponseMessage;
import com.sk.taskmanagementsystem.entity.Task;
import com.sk.taskmanagementsystem.model.TaskAddResource;
import com.sk.taskmanagementsystem.repository.TaskRepository;
import com.sk.taskmanagementsystem.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public ResponseEntity<List<Task>> getAllTask() {
        List<Task> taskList = this.taskRepository.findAll();

        if(taskList.isEmpty()){
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NO_CONTENT);
        }
        taskList = taskList
                .stream()
                .sorted((t1, t2) -> t2.getTaskId().compareTo(t1.getTaskId()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(taskList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseMessage<Task>> createNewTask(TaskAddResource taskAddResource) {

        try {
            Task task = new Task();

            task.setTaskName(taskAddResource.getTaskName());
            task.setTaskDescription(taskAddResource.getTaskDescription());
            task.setTaskPriority(taskAddResource.getTaskPriority());
            task.setTaskStatus(taskAddResource.getTaskStatus());

            Task newTask = this.taskRepository.save(task);

            ResponseMessage<Task> responseMessage = new ResponseMessage<>();
            responseMessage.setMessage("Successfully task created!");
            responseMessage.setValue(newTask);

            return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);

        }catch (Exception exception){
            log.warn("Create New Task: ",exception.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> getById(Long taskId) {
        Optional<Task> optionalTask = this.taskRepository.findById(taskId);
        if(optionalTask.isEmpty()){
            ResponseMessage<String> responseMessage = new ResponseMessage<>();
            responseMessage.setMessage("Task not found");
            return new ResponseEntity<>(responseMessage,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalTask.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Task> updateTask(Task task) {
        try {
            return new ResponseEntity<>(this.taskRepository.save(task), HttpStatus.OK);
        }catch (Exception exception){
            log.warn("Update Task: "+exception.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> deleteTask(Long taskId) {

        Optional<Task> taskOptional = this.taskRepository.findById(taskId);

        if(taskOptional.isEmpty()){
            ResponseMessage<String> responseMessage = new ResponseMessage<>();
            responseMessage.setMessage("Task not found");
            return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
        }

        this.taskRepository.deleteById(taskId);

        ResponseMessage<String> responseMessage = new ResponseMessage<>();
        responseMessage.setMessage("Task deleted successfully!");

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateTaskStatusById(Long taskId, String taskStatus) {
        try {
            return new ResponseEntity<>(this.taskRepository.updateTaskStatusById(taskId, taskStatus), HttpStatus.OK);
        }catch (Exception exception){
            log.warn("Update Task status by ID: "+exception.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
