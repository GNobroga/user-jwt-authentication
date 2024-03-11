package com.apiauth.jpa;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apiauth.concrete.Role;

public interface IRoleJpaRepository extends JpaRepository<Role, UUID> {
    
    Optional<Role> findByName(String name);
}
