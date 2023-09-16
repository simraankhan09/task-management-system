package com.sk.taskmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long taskId;

    @NotBlank(message = "Task name is mandatory")
    private String taskName;

    @NotBlank(message = "Task description is mandatory")
    @Column(length = 300)
    private String taskDescription;

    @NotBlank(message = "Task priority level is mandatory")
    @Pattern(regexp = "LOW|MEDIUM|HIGH|HIGHEST", message = "Invalid task priority value")
    private String taskPriority;

    @Pattern(regexp = "TODO|INPROGRESS|DONE", message = "Invalid task status")
    private String taskStatus;

    @CreationTimestamp
    private Instant createdDate;

    @UpdateTimestamp
    private Instant lastModifiedDate;

}
