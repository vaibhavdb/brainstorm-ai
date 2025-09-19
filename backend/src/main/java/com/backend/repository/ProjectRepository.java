package com.backend.repository;

import com.backend.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<ProjectEntity, UUID> {
    Optional<ProjectEntity> findByRepoUrl(String repoUrl);
}