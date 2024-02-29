package com.bookingtable.servicies;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPaginationService {
    public <T> Page findPaginated(List<T> items, Pageable pageable);
}