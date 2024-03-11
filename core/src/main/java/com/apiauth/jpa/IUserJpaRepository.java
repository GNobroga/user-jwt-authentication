package com.apiauth.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.apiauth.concrete.User;

public interface IUserJpaRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    
}
