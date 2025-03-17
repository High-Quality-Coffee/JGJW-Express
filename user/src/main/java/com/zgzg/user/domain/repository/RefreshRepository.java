package com.zgzg.user.domain.repository;

import com.zgzg.user.domain.model.RefreshToken;
import com.zgzg.user.domain.model.User;

import java.util.Optional;

public interface RefreshRepository {
    Optional<RefreshToken> save(RefreshToken refreshToken);

    Boolean existsByRefresh(String refresh);

    void deleteByRefresh(String refresh);
}
