package com.zgzg.user.domain.repository;

import com.zgzg.user.domain.model.DeliveryStatus;
import com.zgzg.user.domain.model.DeliveryUser;
import com.zgzg.user.domain.model.RefreshToken;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface DeliveryUserRepository {
    @Transactional
    Optional<DeliveryUser> save(DeliveryUser deliveryUser);
}
