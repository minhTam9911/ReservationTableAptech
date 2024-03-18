package com.bookingtable.helpers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;

public class FileHelper {

    @Autowired
    private static ServletContext context;
    private static String randomNameFile() {
    	return UUID.randomUUID().toString().replace("-", "");
    }
    public static String uploadDinnerTable(MultipartFile file) {

        if (file.isEmpty()) {
            return "No file";
        }
        try {
            final Path directory = Paths.get("src/main/resources/static/uploads/dinnerTables");
            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            var filename = randomNameFile()+"."+extension;
            final Path filePath = Paths.get("src/main/resources/static/uploads/dinnerTables/" +filename);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            Files.write(filePath, file.getBytes());
            return filename;
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    
    public static String uploadAvatar(MultipartFile file) {

        if (file.isEmpty()) {
            return "No file";
        }
        try {
            final Path directory = Paths.get("src/main/resources/static/uploads/avatars");
            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            var filename = randomNameFile()+"."+extension;
            final Path filePath = Paths.get("src/main/resources/static/uploads/avatars/" + filename);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            Files.write(filePath, file.getBytes());
            return filename;
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static String uploadRestaurant(MultipartFile file) {

        if (file.isEmpty()) {
            return "No file";
        }
        try {
            final Path directory = Paths.get("src/main/resources/static/uploads/restaurants");
            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            var filename = randomNameFile()+"."+extension;
            final Path filePath = Paths.get("src/main/resources/static/uploads/restaurants/" + filename);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            Files.write(filePath, file.getBytes());
            return filename;
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    
    public static String uploadCategoryDinnerTable(MultipartFile file) {

        if (file.isEmpty()) {
            return "No file";
        }
        try {
            final Path directory = Paths.get("src/main/resources/static/categories/image-dinnerTable");
            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            var filename = randomNameFile()+"."+extension;
            final Path filePath = Paths.get("src/main/resources/static/categories/image-dinnerTable/"+filename);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            Files.write(filePath, file.getBytes());
            
            return filename;
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static String uploadCategoryRestaurant(MultipartFile file) {

        if (file.isEmpty()) {
            return "No file";
        }
        try {
            final Path directory = Paths.get("src/main/resources/static/categories/image-restaurant");
            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            var filename = randomNameFile()+"."+extension;
            final Path filePath = Paths.get("src/main/resources/static/categories/image-restaurant/" + filename);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            Files.write(filePath, file.getBytes());
            return filename;
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    
    public static void deleteCategoryDinnerTable(String filename) {
        try {
            Path filePath = Paths.get("src/main/resources/static/catgories/image-dinnerTable/" + filename);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void deleteCategoryRestaurant(String filename) {
        try {
            Path filePath = Paths.get("src/main/resources/static/categories/image-restaurant/" + filename);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteDinnerTableImage(String filename) {
        try {
            Path filePath = Paths.get("src/main/resources/static/uploads/dinnerTables/" + filename);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteRestaurantImage(String filename) {
        try {
            Path filePath = Paths.get("src/main/resources/static/uploads/restaurants/" + filename);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
