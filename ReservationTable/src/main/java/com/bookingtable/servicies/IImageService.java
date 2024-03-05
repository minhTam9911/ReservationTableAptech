package com.bookingtable.servicies;

import com.bookingtable.dtos.DinnerTableDto;
import com.bookingtable.dtos.ImageDto;
import com.bookingtable.models.Image;

import java.util.List;
import java.util.Set;

public interface IImageService {
    public List<ImageDto> getAllImages();

    public ImageDto getImageById(Integer id);

    public boolean createImage(ImageDto imageDto) ;
    public boolean updateImage(Integer id, ImageDto imageDto);

    public boolean deleteImage(Integer id);
    public Set<ImageDto> getImagesByDinnerTableId(Integer dinnerTableId);

}
