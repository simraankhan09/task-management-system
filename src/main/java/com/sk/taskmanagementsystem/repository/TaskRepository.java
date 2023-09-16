package com.sk.taskmanagementsystem.repository;

import com.sk.taskmanagementsystem.entity.Task;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(
            value = "UPDATE Task t SET t.taskStatus = :taskStatus WHERE t.taskId = :taskId"
    )
    @Transactional
    @Modifying
    Integer updateTaskStatusById(Long taskId, String taskStatus);

}
