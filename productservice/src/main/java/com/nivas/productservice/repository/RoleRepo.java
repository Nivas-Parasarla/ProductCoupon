package com.nivas.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nivas.productservice.model.Role;

public interface RoleRepo extends JpaRepository<Role,Long> {

}
