package com.zgzg.user.domain.repository;

import com.zgzg.user.domain.model.RefreshToken;
import com.zgzg.user.domain.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RefreshRepository {
    @Transactional
    Optional<RefreshToken> save(RefreshToken refreshToken);

    Boolean existsByRefresh(String refresh);
    @Transactional
    void deleteByRefresh(String refresh);
}
