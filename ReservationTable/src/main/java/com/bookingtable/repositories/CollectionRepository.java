package com.bookingtable.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookingtable.models.Collection;
import com.bookingtable.models.Invoice;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Integer>  {

}
