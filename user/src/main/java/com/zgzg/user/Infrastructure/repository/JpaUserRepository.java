package com.zgzg.user.Infrastructure.repository;

import com.zgzg.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
}
