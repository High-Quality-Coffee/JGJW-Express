package com.zgzg.user.Infrastructure.repository;

import com.zgzg.user.domain.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRefreshRepository extends JpaRepository<RefreshToken,Long> {
    Boolean existsByRefresh(String refresh);

    void deleteByRefresh(String refresh);
}
