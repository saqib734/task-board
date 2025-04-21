package com.worldline.taskboard.api.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskList {
  private Long id;
  private String name;
  private List<Task> tasks;
}
