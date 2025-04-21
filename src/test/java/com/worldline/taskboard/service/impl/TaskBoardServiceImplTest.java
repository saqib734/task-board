package com.worldline.taskboard.service.impl;

import com.worldline.taskboard.entity.ListEntity;
import com.worldline.taskboard.entity.TaskEntity;
import com.worldline.taskboard.exceptions.NotFoundException;
import com.worldline.taskboard.repository.ListRepository;
import com.worldline.taskboard.repository.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskBoardServiceImplTest {
  @Mock
  private ListRepository listRepository;

  @Mock
  private TaskRepository taskRepository;

  @InjectMocks
  private TaskBoardServiceImpl listService;

  private AutoCloseable autoCloseable;

  @BeforeEach
  void setUp() {
    autoCloseable = MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void tearDown() throws Exception {
    autoCloseable.close();
  }

  @Test
  void getAllLists_returnsList() {
    // Given
    List<ListEntity> mockLists = List.of(new ListEntity());
    when(listRepository.findAll()).thenReturn(mockLists);

    // When
    List<ListEntity> result = listService.getAllLists();

    // Then
    assertEquals(1, result.size());
    verify(listRepository).findAll();
  }

  @Test
  void createList_savesAndReturnsList() {
    // Given
    ListEntity input = new ListEntity();
    input.setName("New List");

    when(listRepository.save(any(ListEntity.class))).thenReturn(input);

    // When
    ListEntity result = listService.createList("New List");

    // Then
    assertEquals("New List", result.getName());
    verify(listRepository).save(any(ListEntity.class));
  }

  @Test
  void addTaskToList_success() {
    // Given
    ListEntity list = new ListEntity();
    list.setId(1L);
    when(listRepository.findById(1L)).thenReturn(Optional.of(list));

    TaskEntity task = new TaskEntity();
    when(taskRepository.save(any(TaskEntity.class))).thenReturn(task);

    // When
    TaskEntity result = listService.addTaskToList(1L, "Task Name", "Desc");

    // Then
    assertNotNull(result);
    verify(taskRepository).save(any(TaskEntity.class));
  }

  @Test
  void addTaskToList_listNotFound_throwsException() {
    // Given
    when(listRepository.findById(1L)).thenReturn(Optional.empty());

    // When & Then
    assertThrows(NotFoundException.class, () -> listService.addTaskToList(1L, "Task", "Desc"));
  }

  @Test
  void updateTask_success() {
    // Given
    TaskEntity task = new TaskEntity();
    when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
    when(taskRepository.save(any(TaskEntity.class))).thenReturn(task);

    // When
    TaskEntity result = listService.updateTask(1L, "Updated", "Updated Desc");

    // Then
    assertEquals("Updated", result.getName());
    assertEquals("Updated Desc", result.getDescription());
  }

  @Test
  void updateTask_taskNotFound_throwsException() {
    // Given
    when(taskRepository.findById(1L)).thenReturn(Optional.empty());

    // When & Then
    assertThrows(NotFoundException.class, () -> listService.updateTask(1L, "name", "desc"));
  }

  @Test
  void deleteTask_success() {
    // Given
    TaskEntity task = new TaskEntity();
    when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

    // When
    listService.deleteTask(1L);

    // Then
    verify(taskRepository).delete(task);
  }

  @Test
  void deleteList_success() {
    // Given
    ListEntity list = new ListEntity();
    list.setTasks(new ArrayList<>());
    when(listRepository.findById(1L)).thenReturn(Optional.of(list));

    // When
    listService.deleteList(1L);

    // Then
    verify(taskRepository).deleteAll(anyList());
    verify(listRepository).delete(list);
  }

  @Test
  void deleteList_listNotFound_throwsException() {
    // Given
    when(listRepository.findById(1L)).thenReturn(Optional.empty());

    // When & Then
    assertThrows(NotFoundException.class, () -> listService.deleteList(1L));
  }

  @Test
  void moveTaskToAnotherList_success() {
    // Given
    TaskEntity task = new TaskEntity();
    ListEntity newList = new ListEntity();

    when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
    when(listRepository.findById(2L)).thenReturn(Optional.of(newList));
    when(taskRepository.save(any(TaskEntity.class))).thenReturn(task);

    // When
    TaskEntity result = listService.moveTaskToAnotherList(1L, 2L);

    // Then
    assertEquals(newList, result.getList());
    verify(taskRepository).save(task);
  }

  @Test
  void moveTaskToAnotherList_taskNotFound() {
    when(taskRepository.findById(1L)).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class, () -> listService.moveTaskToAnotherList(1L, 2L));
  }

  @Test
  void moveTaskToAnotherList_listNotFound() {
    // Given
    TaskEntity task = new TaskEntity();
    when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
    when(listRepository.findById(2L)).thenReturn(Optional.empty());

    // When & Then
    assertThrows(NotFoundException.class, () -> listService.moveTaskToAnotherList(1L, 2L));
  }
}