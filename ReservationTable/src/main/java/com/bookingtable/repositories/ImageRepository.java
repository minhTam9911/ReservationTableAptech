package com.bookingtable.repositories;

import com.bookingtable.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository

public interface ImageRepository extends JpaRepository<Image, Integer> {
    Set<Image> findByDinnerTableId(Integer dinnerTableId);

}
