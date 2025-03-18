package com.zgzg.user.Infrastructure.repository;

import com.zgzg.user.domain.model.RefreshToken;
import com.zgzg.user.domain.repository.RefreshRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshRepositoryImpl implements RefreshRepository {

    private final JpaRefreshRepository jpaRefreshRepository;

    @Override
    public Optional<RefreshToken> save(RefreshToken refreshToken) {
        return Optional.of(jpaRefreshRepository.save(refreshToken));
    }

    @Override
    public Boolean existsByRefresh(String refresh) {
       return jpaRefreshRepository.existsByRefresh(refresh);
    }

    @Override
    @Transactional
    public void deleteByRefresh(String refresh) {
        jpaRefreshRepository.deleteByRefresh(refresh);
    }
}
