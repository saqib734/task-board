package com.worldline.taskboard.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskListInput {
  @NotBlank(message = "List name is required")
  @Size(max = 50, message = "List name must not exceed 50 characters")
  private String name;
}
