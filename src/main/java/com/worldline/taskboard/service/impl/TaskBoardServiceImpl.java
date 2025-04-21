package com.worldline.taskboard.service.impl;

import com.worldline.taskboard.entity.ListEntity;
import com.worldline.taskboard.entity.TaskEntity;
import com.worldline.taskboard.exceptions.NotFoundException;
import com.worldline.taskboard.repository.ListRepository;
import com.worldline.taskboard.repository.TaskRepository;
import com.worldline.taskboard.service.TaskBoardService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TaskBoardServiceImpl implements TaskBoardService {

  private final ListRepository listRepository;
  private final TaskRepository taskRepository;

  @Override
  public List<ListEntity> getAllLists() {
    return listRepository.findAll();
  }

  @Override
  public ListEntity createList(String name) {
    ListEntity listEntity = new ListEntity();
    listEntity.setName(name);
    return listRepository.save(listEntity);
  }

  @Override
  public TaskEntity addTaskToList(Long listId, String name, String description) {
    ListEntity list = listRepository.findById(listId)
        .orElseThrow(() -> new NotFoundException("List not found"));

    TaskEntity taskEntity = new TaskEntity();
    taskEntity.setList(list);
    taskEntity.setName(name);
    taskEntity.setDescription(description);
    return taskRepository.save(taskEntity);
  }

  @Override
  public TaskEntity updateTask(Long taskId, String name, String description) {
    TaskEntity task = taskRepository.findById(taskId)
        .orElseThrow(() -> new NotFoundException("Task not found"));

    task.setName(name);
    task.setDescription(description);
    return taskRepository.save(task);
  }

  @Override
  public void deleteTask(Long taskId) {
    TaskEntity task = taskRepository.findById(taskId)
        .orElseThrow(() -> new NotFoundException("Task not found"));

    taskRepository.delete(task);
  }

  @Override
  public void deleteList(Long listId) {
    ListEntity list = listRepository.findById(listId)
        .orElseThrow(() -> new NotFoundException("List not found"));

    taskRepository.deleteAll(list.getTasks());
    listRepository.delete(list);
  }

  @Override
  public TaskEntity moveTaskToAnotherList(Long taskId, Long newListId) {
    TaskEntity task = taskRepository.findById(taskId)
        .orElseThrow(() -> new NotFoundException("Task not found"));

    ListEntity list = listRepository.findById(newListId)
        .orElseThrow(() -> new NotFoundException("List not found"));

    task.setList(list);
    return taskRepository.save(task);
  }
}
