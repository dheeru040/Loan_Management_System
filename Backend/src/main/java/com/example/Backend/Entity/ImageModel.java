package com.example.Backend.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "image_model")
public class ImageModel {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "imageModel")
    private List<ImageFile> imageFiles;

    public ImageModel() {
    }

    public ImageModel(Long id, String name, String type, LocalDateTime creationTime, List<ImageFile> imageFiles) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.creationTime = creationTime;
        this.imageFiles = imageFiles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public List<ImageFile> getImageFiles() {
        return imageFiles;
    }

    public void setImageFiles(List<ImageFile> imageFiles) {
        this.imageFiles = imageFiles;
    }
}
