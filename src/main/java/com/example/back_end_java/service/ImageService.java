package com.example.back_end_java.service;

import com.example.back_end_java.entity.Image;
import com.example.back_end_java.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageService {
    @Value("${app.uploads.dir:uploads/}")
    private String uploadsDir;


    public byte[] getImageFile(String filename) throws IOException {
        Path path = Paths.get(uploadsDir + filename);
        return Files.readAllBytes(path);
    }

    public String saveImage(MultipartFile file) throws IOException {
        return localeSave(file);
    }

    private String localeSave(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadsDir);
        if (!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream() , filePath , StandardCopyOption.REPLACE_EXISTING);
        return  filename;
    }
}
