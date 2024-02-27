package com.bookingtable.repositories;

import com.bookingtable.models.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, String> {
}
