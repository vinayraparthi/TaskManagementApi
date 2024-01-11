package com.vinay.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinay.api.entity.Role;
import com.vinay.api.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long>{

	Optional<Users> findByEmail(String email);
	 
	Users findByRole(Role role);

}
