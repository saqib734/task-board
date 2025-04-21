package com.worldline.taskboard.repository;

import com.worldline.taskboard.entity.ListEntity;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<ListEntity, Long> {
  @EntityGraph(attributePaths = "tasks")
  List<ListEntity> findAll();
}