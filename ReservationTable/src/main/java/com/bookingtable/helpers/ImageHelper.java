package com.bookingtable.helpers;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class ImageHelper {

    private static final String UPLOAD_DIR = "uploads";

    public String uploadImage(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Path filePath = uploadPath.resolve(fileName);
        file.transferTo(filePath.toFile());
        return fileName;
    }
    public boolean deleteImage(String fileName, String uploadDir) {
        Path imagePath = Paths.get(uploadDir, fileName);
        try {
            return Files.deleteIfExists(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
