package com.worldline.taskboard.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TaskInput {
  @NotBlank(message = "Task name is required")
  @Size(max = 50, message = "Task name must not exceed 50 characters")
  private String name;

  @NotBlank(message = "Task description is required")
  @Size(max = 500, message = "Task description must not exceed 500 characters")
  private String description;
}
