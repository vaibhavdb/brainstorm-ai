package com.backend.repository;

import com.backend.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface FileRepository extends JpaRepository<FileEntity, UUID> {
    List<FileEntity> findByProjectId(UUID projectId);
}
