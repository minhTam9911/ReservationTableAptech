package com.bookingtable.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookingtable.models.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String>{

}
