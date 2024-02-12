package com.example.Backend.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Backend.Entity.ImageFile;

public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {
    // You can add custom query methods here if needed
}