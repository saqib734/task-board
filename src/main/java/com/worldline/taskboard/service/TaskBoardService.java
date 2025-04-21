package com.worldline.taskboard.service;

import com.worldline.taskboard.entity.ListEntity;
import com.worldline.taskboard.entity.TaskEntity;
import java.util.List;

public interface TaskBoardService {
    List<ListEntity> getAllLists();
    ListEntity createList(String name);
    TaskEntity addTaskToList(Long listId, String name, String description);
    TaskEntity updateTask(Long taskId, String name, String description);
    void deleteTask(Long taskId);
    void deleteList(Long listId);
    TaskEntity moveTaskToAnotherList(Long taskId, Long newListId);
}