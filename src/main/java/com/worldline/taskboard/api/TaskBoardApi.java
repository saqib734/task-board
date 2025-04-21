package com.worldline.taskboard.api;

import com.worldline.taskboard.api.model.Task;
import com.worldline.taskboard.api.model.TaskInput;
import com.worldline.taskboard.api.model.TaskList;
import com.worldline.taskboard.api.model.TaskListInput;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/lists")
public interface TaskBoardApi {

  @GetMapping
  java.util.List<TaskList> getAllListsWithTasks();

  @PostMapping
  TaskList createList(@Valid @RequestBody TaskListInput name);

  @PostMapping("/{listId}/tasks")
  Task addTaskToList(
      @PathVariable Long listId,
      @Valid @RequestBody TaskInput task
  );

  @PutMapping("/tasks/{taskId}")
  Task updateTask(
      @PathVariable Long taskId,
      @Valid @RequestBody TaskInput updatedTask
  );

  @DeleteMapping(value = "/{listId}")
  void deleteList(@PathVariable Long listId);

  @DeleteMapping("/tasks/{taskId}")
  void deleteTask(
      @PathVariable Long taskId
  );

  @PostMapping("/tasks/{taskId}/move/{targetListId}")
  void moveTask(
      @PathVariable Long taskId,
      @PathVariable Long targetListId
  );
}
