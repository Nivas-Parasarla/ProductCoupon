package com.nivas.couponservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nivas.couponservice.model.User;

public interface UserRepo extends JpaRepository<User, Long> {

	User findByEmail(String email);
}
