package com.ignek.ecommerce.apis.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ignek.ecommerce.apis.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username);

//	  Boolean existsByUsernameOrPhoneNumberOrEmail(String username, String phone, String email);
//
	  Boolean existsByUsername(String username);

	  Boolean existsByEmail(String email);

//	    boolean existsByPhoneNumber(String phoneNumber);
//
//	    Optional<User> findByEmail(String email);
}
