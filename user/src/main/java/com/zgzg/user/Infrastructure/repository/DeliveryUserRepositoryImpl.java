package com.zgzg.user.Infrastructure.repository;

import com.zgzg.user.domain.model.DeliveryUser;
import com.zgzg.user.domain.model.RefreshToken;
import com.zgzg.user.domain.repository.DeliveryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DeliveryUserRepositoryImpl implements DeliveryUserRepository {
    private final JpaDeliveryUserRepository jpaDeliveryUserRepository;

    @Override
    @Transactional
    public Optional<DeliveryUser> save(DeliveryUser deliveryUser) {
        return Optional.of(jpaDeliveryUserRepository.save(deliveryUser));
    }
}

