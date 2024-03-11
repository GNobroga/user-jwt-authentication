package com.apiauth.abstraction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.apiauth.concrete.User;

public interface IUserRepository {
    
    User create(User record);

    Page<User> takeAll(Pageable pageable);

    Optional<User> getById(UUID id);

    void update(User record);

    void delete(UUID id);

    boolean existsUsername(String username);

    boolean existsEmail (String email);

    Optional<User> findByUsername(String username);

    List<User> filterByAttributes(User example);

    void addRole(User user, String role);
}
