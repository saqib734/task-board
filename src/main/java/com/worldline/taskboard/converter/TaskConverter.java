package com.worldline.taskboard.converter;

import com.worldline.taskboard.api.model.Task;
import com.worldline.taskboard.entity.TaskEntity;
import java.util.List;

public class TaskConverter {

  private TaskConverter() {}

  public static List<Task> toModels(List<TaskEntity> entities) {
    return entities.stream()
        .map(TaskConverter::toModel)
        .toList();
  }

  public static Task toModel(TaskEntity entity) {
    return new Task(entity.getId(), entity.getName(), entity.getDescription());
  }
}
