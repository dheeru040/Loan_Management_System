package com.example.Backend.Repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Backend.Entity.ImageModel;



public interface ImageRepository extends JpaRepository<ImageModel, Long> {
	Optional<ImageModel> findByName(String name);
}
