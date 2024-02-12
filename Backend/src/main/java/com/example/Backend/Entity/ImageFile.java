package com.example.Backend.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "image_file")
public class ImageFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Lob
    @Column(name = "file_bytes", columnDefinition = "LONGBLOB")
    private byte[] fileBytes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_model_id", nullable = false)
    private ImageModel imageModel;

    @Column(name = "upload_time")
    private LocalDateTime uploadTime;

    public ImageFile() {
    }

    public ImageFile(String name, String type, byte[] fileBytes, ImageModel imageModel, LocalDateTime uploadTime) {
        this.name = name;
        this.type = type;
        this.fileBytes = fileBytes;
        this.imageModel = imageModel;
        this.uploadTime = uploadTime;
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

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public ImageModel getImageModel() {
        return imageModel;
    }

    public void setImageModel(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }
}
