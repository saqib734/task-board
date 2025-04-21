package com.worldline.taskboard.controller;

import com.worldline.taskboard.api.TaskBoardApi;
import com.worldline.taskboard.api.model.Task;
import com.worldline.taskboard.api.model.TaskInput;
import com.worldline.taskboard.api.model.TaskList;
import com.worldline.taskboard.api.model.TaskListInput;
import com.worldline.taskboard.entity.ListEntity;
import com.worldline.taskboard.entity.TaskEntity;
import com.worldline.taskboard.service.TaskBoardService;
import com.worldline.taskboard.converter.TaskConverter;
import com.worldline.taskboard.converter.TaskListConverter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TaskBoardController implements TaskBoardApi {

  private final TaskBoardService taskBoardService;

  @Override
  public List<TaskList> getAllListsWithTasks() {
    List<ListEntity> allLists = taskBoardService.getAllLists();
    return TaskListConverter.toModels(allLists);
  }

  @Override
  public TaskList createList(TaskListInput newTaskList) {
    ListEntity list = taskBoardService.createList(newTaskList.getName());

    return TaskListConverter.toModel(list);
  }

  @Override
  public Task addTaskToList(Long listId, TaskInput newTask) {
    TaskEntity taskEntity = taskBoardService.addTaskToList(listId, newTask.getName(), newTask.getDescription());

    return TaskConverter.toModel(taskEntity);
  }

  @Override
  public Task updateTask(Long taskId, TaskInput updatedTask) {
    TaskEntity task = taskBoardService.updateTask(taskId, updatedTask.getName(), updatedTask.getDescription());

    return TaskConverter.toModel(task);
  }

  @Override
  public void deleteList(Long listId) {
    taskBoardService.deleteList(listId);
  }

  @Override
  public void deleteTask(Long taskId) {
    taskBoardService.deleteTask(taskId);
  }

  @Override
  public void moveTask(Long taskId, Long targetListId) {
    taskBoardService.moveTaskToAnotherList(taskId, targetListId);
  }
}
