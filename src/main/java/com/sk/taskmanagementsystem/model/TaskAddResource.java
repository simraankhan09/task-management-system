package com.sk.taskmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskAddResource {

    @NotBlank(message = "Task name is mandatory")
    private String taskName;

    @NotBlank(message = "Task description is mandatory")
    private String taskDescription;

    @NotBlank(message = "Task priority level is mandatory")
    @Pattern(regexp = "LOW|MEDIUM|HIGH|HIGHEST",message = "Invalid task priority value")
    private String taskPriority;

    @JsonIgnore
    private String taskStatus = "TODO";
}
