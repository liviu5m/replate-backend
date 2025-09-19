package com.replate.backend.controller;

import com.replate.backend.dto.FileDto;
import com.replate.backend.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/cloudinary")
public class CloudinaryController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String url = cloudinaryService.uploadFile(file);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }

}
