package com.nivas.couponservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nivas.couponservice.model.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {

}
