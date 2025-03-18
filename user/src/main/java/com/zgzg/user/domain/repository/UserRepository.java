package com.zgzg.user.domain.repository;

import com.zgzg.user.domain.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository {
    // 유저 정보를 저장
    Optional<User> save(User user);

    Optional<User> findByUsername(String username);
}
