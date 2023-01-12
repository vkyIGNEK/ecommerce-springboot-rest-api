package com.ignek.ecommerce.apis.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ignek.ecommerce.apis.entity.ERole;
import com.ignek.ecommerce.apis.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Long>{
	
	Optional<Role> findByName(ERole name);
	
	

}
