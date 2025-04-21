package com.worldline.taskboard.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.worldline.taskboard.api.model.TaskInput;
import com.worldline.taskboard.api.model.TaskListInput;
import com.worldline.taskboard.entity.ListEntity;
import com.worldline.taskboard.entity.TaskEntity;
import com.worldline.taskboard.service.TaskBoardService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TaskBoardController.class)
class TasksControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TaskBoardService taskBoardService;

  @Autowired
  private ObjectMapper objectMapper;

  private ListEntity sampleList;
  private TaskEntity sampleTask;

  @BeforeEach
  void setup() {
    sampleList = new ListEntity(1L, "Work", List.of());
    sampleList.setId(1L);

    sampleTask = new TaskEntity(1L, "Email Client", "Send project updates", sampleList);
    sampleTask.setId(1L);
  }

  @Test
  void shouldReturnAllLists() throws Exception {
    Mockito.when(taskBoardService.getAllLists()).thenReturn(List.of(sampleList));

    mockMvc.perform(get("/api/v1/lists"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)));
  }

  @Test
  void shouldCreateNewList() throws Exception {
    Mockito.when(taskBoardService.createList(any(String.class))).thenReturn(sampleList);

    mockMvc.perform(post("/api/v1/lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new TaskListInput("Work"))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Work"));
  }

  @Test
  void shouldAddTaskToList() throws Exception {
    Mockito.when(taskBoardService.addTaskToList(eq(1L), anyString(), anyString())).thenReturn(sampleTask);

    mockMvc.perform(post("/api/v1/lists/1/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new TaskInput("Email Client", "Send project updates"))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Email Client"));
  }

  @Test
  void shouldUpdateTask() throws Exception {
    sampleTask.setName("Updated Task");
    Mockito.when(taskBoardService.updateTask(eq(1L), anyString(), anyString())).thenReturn(sampleTask);

    mockMvc.perform(put("/api/v1/lists/tasks/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new TaskInput("Email Client", "Send project updates"))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Updated Task"));
  }

  @Test
  void shouldDeleteTask() throws Exception {
    mockMvc.perform(delete("/api/v1/lists/tasks/1"))
        .andExpect(status().isOk());
  }

  @Test
  void shouldDeleteList() throws Exception {
    mockMvc.perform(delete("/api/v1/lists/1"))
        .andExpect(status().isOk());
  }

  @Test
  void shouldMoveTaskToAnotherList() throws Exception {
    mockMvc.perform(post("/api/v1/lists/tasks/1/move/2"))
        .andExpect(status().isOk());
  }
}