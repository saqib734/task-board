package com.worldline.taskboard.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Task {
  private Long id;
  private String name;
  private String description;
}
