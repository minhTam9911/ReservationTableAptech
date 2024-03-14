package com.bookingtable.helpers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;

public class FileHelper {

    @Autowired
    private static ServletContext context;

    public static String uploadDinnerTable(MultipartFile file) {

        if (file.isEmpty()) {
            return "No file";
        }
        try {
            final Path directory = Paths.get("src/main/resources/static/uploads/dinnerTables");
            final Path filePath = Paths.get("src/main/resources/static/uploads/dinnerTables/" + file.getOriginalFilename());
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            Files.write(filePath, file.getBytes());
            return file.getOriginalFilename();
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
            final Path filePath = Paths.get("src/main/resources/static/uploads/restaurants/" + file.getOriginalFilename());
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            Files.write(filePath, file.getBytes());
            return file.getOriginalFilename();
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
            final Path filePath = Paths.get("src/main/resources/static/categories/image-dinnerTable/" + file.getOriginalFilename());
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            Files.write(filePath, file.getBytes());
            return file.getOriginalFilename();
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
            final Path filePath = Paths.get("src/main/resources/static/categories/image-restaurant/" + file.getOriginalFilename());
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            Files.write(filePath, file.getBytes());
            return file.getOriginalFilename();
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
