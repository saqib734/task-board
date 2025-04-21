package com.worldline.taskboard;


import static org.assertj.core.api.Assertions.assertThat;

import com.worldline.taskboard.api.model.Task;
import com.worldline.taskboard.api.model.TaskInput;
import com.worldline.taskboard.api.model.TaskList;
import com.worldline.taskboard.api.model.TaskListInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoTaskListIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;

    @BeforeEach
    void setUp() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void testCreateListAndAddTask() {
        // Create a list
        TaskListInput listInput = new TaskListInput("Groceries");
        HttpEntity<TaskListInput> listRequest = new HttpEntity<>(listInput, headers);

        ResponseEntity<TaskList> listResponse = restTemplate.postForEntity("/api/v1/lists", listRequest, TaskList.class);
        assertThat(listResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        TaskList createdList = listResponse.getBody();
        assertThat(createdList).isNotNull();
        assertThat(createdList.getName()).isEqualTo("Groceries");

        // Add a task to the list
        TaskInput taskInput = new TaskInput("Buy milk", "Buy 2 liters of milk");
        HttpEntity<TaskInput> taskRequest = new HttpEntity<>(taskInput, headers);

        ResponseEntity<Task> taskResponse = restTemplate.postForEntity(
            String.format("/api/v1/lists/%d/tasks", createdList.getId()), taskRequest, Task.class);
        assertThat(taskResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Task task = taskResponse.getBody();
        assertThat(task).isNotNull();
        assertThat(task.getName()).isEqualTo("Buy milk");
    }

    @Test
    void testGetAllListsWithTasks() {
        ResponseEntity<TaskList[]> response = restTemplate.getForEntity("/api/v1/lists", TaskList[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        TaskList[] lists = response.getBody();
        assertThat(lists).isNotNull();
    }
}