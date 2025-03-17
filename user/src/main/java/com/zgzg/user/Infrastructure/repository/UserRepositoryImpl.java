package com.zgzg.user.Infrastructure.repository;

import com.zgzg.user.domain.model.User;
import com.zgzg.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public Optional<User> save(User user) {
        return Optional.of(jpaUserRepository.save(user));
    }
}
