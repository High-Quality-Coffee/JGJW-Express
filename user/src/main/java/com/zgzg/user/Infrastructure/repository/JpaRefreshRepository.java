package com.zgzg.user.Infrastructure.repository;

import com.zgzg.user.domain.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface JpaRefreshRepository extends JpaRepository<RefreshToken,Long> {
    Boolean existsByRefresh(String refresh);

    @Transactional
    void deleteByRefresh(String refresh);
}
