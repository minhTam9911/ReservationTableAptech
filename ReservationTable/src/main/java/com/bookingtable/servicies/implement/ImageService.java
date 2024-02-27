package com.bookingtable.servicies.implement;

import com.bookingtable.dtos.ImageDto;
import com.bookingtable.mappers.ImageMapper;
import com.bookingtable.models.Image;
import com.bookingtable.repositories.ImageRepository;
import com.bookingtable.servicies.IImageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class ImageService implements IImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public List<ImageDto> getAllImages() {
        return imageRepository.findAll().stream()
                .map(ImageMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ImageDto getImageById(Integer id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + id));
        return ImageMapper.mapToDto(image);
    }

    @Override
    public boolean createImage(ImageDto imageDto) {
        Image image = ImageMapper.mapToModel(imageDto);
        Image savedImage = imageRepository.save(image);
        return ImageMapper.mapToDto(savedImage)!=null;
    }

    @Override
    public boolean updateImage(Integer id, ImageDto imageDto) {
        Image existingImage = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + id));

        Image updatedImage = ImageMapper.mapToModel(imageDto);
        updatedImage.setId(existingImage.getId()); // Ensure the ID remains the same

        Image savedImage = imageRepository.save(updatedImage);
        return ImageMapper.mapToDto(savedImage)!=null;
    }

    @Override
    public boolean deleteImage(Integer id) {
        if (imageRepository.existsById(id)) {
            imageRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
