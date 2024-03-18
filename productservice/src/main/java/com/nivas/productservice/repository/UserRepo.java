package com.nivas.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nivas.productservice.model.User;

public interface UserRepo extends JpaRepository<User, Long> {

	 User findByEmail(String username);

}
