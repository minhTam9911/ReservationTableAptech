package com.bookingtable.repositories;

import com.bookingtable.models.Guest;
import com.bookingtable.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository  extends JpaRepository<Permission, Integer> {
}
