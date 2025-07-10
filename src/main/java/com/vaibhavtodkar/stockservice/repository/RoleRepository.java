package com.vaibhavtodkar.stockservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vaibhavtodkar.stockservice.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	Optional<Role> findByName(String name);
}