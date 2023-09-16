package com.sk.taskmanagementsystem.service;

import com.sk.taskmanagementsystem.common.ResponseMessage;
import com.sk.taskmanagementsystem.entity.Task;
import com.sk.taskmanagementsystem.model.TaskAddResource;
import com.sk.taskmanagementsystem.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class TaskServiceImplTest {

    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp(){
    }

    @Test
    public void testGetAllTask(){
       List<Task> testTaskList = new ArrayList<>();

        testTaskList.add(
                Task.builder()
                        .taskId(1L)
                        .taskName("Test Task 0")
                        .taskDescription("Test Description 0")
                        .taskPriority("LOW")
                        .taskStatus("TODO")
                        .createdDate(Instant.now())
                        .lastModifiedDate(Instant.now())
                        .build()
        );

        Mockito
                .when(this.taskRepository.findAll())
                .thenReturn(testTaskList);

        List<Task> taskList = this.taskService.getAllTask().getBody();
        Assertions.assertIterableEquals(testTaskList, taskList);

    }

    @Test
    public void  testGetAllTaskEmpty(){
        List<Task> emptyTask = new ArrayList<>();
        Mockito
                .when(this.taskRepository.findAll())
                .thenReturn(emptyTask);

        List<Task> taskList = this.taskService.getAllTask().getBody();

        Assertions.assertIterableEquals(emptyTask, taskList);
    }

    @Test
    public void testCreateNewTask(){

        TaskAddResource testAddResource = TaskAddResource.builder()
                .taskName("Test Task 0")
                .taskDescription("Test Description 0")
                .taskPriority("LOW")
                .build();

        Task testNewTask = Task.builder()
                .taskName(testAddResource.getTaskName())
                .taskDescription(testAddResource.getTaskDescription())
                .taskPriority(testAddResource.getTaskPriority())
                .taskStatus("TODO")
                .createdDate(Instant.now())
                .lastModifiedDate(Instant.now())
                .build();

        Mockito.when(this.taskRepository.save(testNewTask))
                .thenReturn(testNewTask);

        ResponseMessage<Task> testResponseMessage = new ResponseMessage<>();
        testResponseMessage.setMessage("Successfully task created!");


        String message = this.taskService.createNewTask(testAddResource).getBody().getMessage();
        Assertions.assertEquals(message, testResponseMessage.getMessage());

    }

    @Test
    public void testGetById(){

        Optional<Task> testTask = Optional.of(Task.builder()
                .taskId(1L)
                .taskName("Test Task 0")
                .taskDescription("Test task description 0")
                .taskPriority("LOW")
                .taskStatus("TODO")
                .createdDate(Instant.now())
                .lastModifiedDate(Instant.now())
                .build()
        );

        Mockito.when(this.taskRepository.findById(1L))
                .thenReturn(testTask);

        ResponseEntity<Object> response = this.taskService.getById(1L);

        if(response.getBody() instanceof Task){
            Assertions.assertEquals(response.getBody(),testTask.get());
        }

    }

    @Test
    public void testGetByIdWithInvalidId(){

        Optional<Task> testTask = Optional.of(Task.builder()
                .taskId(1L)
                .taskName("Test Task 0")
                .taskDescription("Test task description 0")
                .taskPriority("LOW")
                .taskStatus("TODO")
                .createdDate(Instant.now())
                .lastModifiedDate(Instant.now())
                .build()
        );

        Mockito.when(this.taskRepository.findById(1L))
                .thenReturn(testTask);

        ResponseEntity<Object> response = this.taskService.getById(2L);

        if(response.getBody() instanceof ResponseMessage<?>){
            Assertions.assertEquals("Task not found",((ResponseMessage<?>) response.getBody()).getMessage());
        }

    }

    @Test
    public void testUpdateTask(){

        Task task = Task.builder()
                .taskId(1L)
                .taskName("Test Task 0")
                .taskDescription("Test Description 0")
                .taskPriority("LOW")
                .taskStatus("TODO")
                .createdDate(Instant.now())
                .lastModifiedDate(Instant.now())
                .build();

        Mockito.when(this.taskRepository.save(task))
                .thenReturn(task);

        ResponseEntity<Task> response = this.taskService.updateTask(task);

        Assertions.assertEquals(task, response.getBody());

    }

}
