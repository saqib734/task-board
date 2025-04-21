package com.worldline.taskboard.converter;

import com.worldline.taskboard.api.model.TaskList;
import com.worldline.taskboard.entity.ListEntity;
import java.util.List;

public class TaskListConverter {

  private TaskListConverter() {}

  public static TaskList toModel(ListEntity entity) {
    return new TaskList(entity.getId(), entity.getName(), TaskConverter.toModels(entity.getTasks()));
  }

  public static List<TaskList> toModels(List<ListEntity> entities) {
    return entities.stream()
        .map(TaskListConverter::toModel)
        .toList();
  }
}
