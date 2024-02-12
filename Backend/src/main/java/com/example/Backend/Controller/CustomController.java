package com.example.Backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.Backend.Entity.ImageFile;
import com.example.Backend.Entity.ImageModel;

import com.example.Backend.Repository.ImageRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "**")
public class CustomController {
	@GetMapping("/api/login")
    public String currentUserName(Authentication authentication) {
        return authentication.getName();
    }
	@Autowired
    private ImageRepository imageModelRepository;

   
    @PostMapping("/upload/{id}")
    public BodyBuilder uploadImage(@RequestParam("files") List<MultipartFile> files, @PathVariable Long id) throws IOException {

        ImageModel imageModel = new ImageModel();
        imageModel.setId(id);
        imageModel.setCreationTime(LocalDateTime.now()); // Set the creation time

        List<ImageFile> imageFiles = new ArrayList<>();

        for (MultipartFile file : files) {
            System.out.println("Original File Byte Size - " + file.getBytes().length);

            ImageFile imageFile = new ImageFile();
            imageFile.setName(file.getOriginalFilename());
            imageFile.setType(file.getContentType());
            imageFile.setFileBytes(file.getBytes());
            imageFile.setImageModel(imageModel);
            imageFile.setUploadTime(LocalDateTime.now()); // Set the upload time

            imageFiles.add(imageFile);
        }

        imageModel.setImageFiles(imageFiles);
        imageModelRepository.save(imageModel);

        return ResponseEntity.status(HttpStatus.OK);
    }

    @GetMapping(path = { "/get/{imageModelId}/{imageFileId}" })
    public ResponseEntity<byte[]> getFile(
            @PathVariable("imageModelId") Long imageModelId,
            @PathVariable("imageFileId") Long imageFileId) throws IOException {

        Optional<ImageModel> optionalImageModel = imageModelRepository.findById(imageModelId);

        if (optionalImageModel.isPresent()) {
            ImageModel imageModel = optionalImageModel.get();
            Optional<ImageFile> optionalImageFile = imageModel.getImageFiles()
                    .stream()
                    .filter(imageFile -> imageFile.getId().equals(imageFileId))
                    .findFirst();

            if (optionalImageFile.isPresent()) {
                ImageFile imageFile = optionalImageFile.get();

                return ResponseEntity.ok()
                        .header("Content-Disposition", "attachment; filename=" + imageFile.getName())
                        .body(imageFile.getFileBytes());
            }
        }

        return ResponseEntity.notFound().build();
    }
	
}
