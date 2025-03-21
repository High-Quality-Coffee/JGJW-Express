package com.zgzg.user.domain.repository;

import com.zgzg.user.domain.model.DeliveryStatus;
import com.zgzg.user.domain.model.DeliveryType;
import com.zgzg.user.domain.model.DeliveryUser;
import com.zgzg.user.domain.model.RefreshToken;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface DeliveryUserRepository {
    @Transactional
    Optional<DeliveryUser> save(DeliveryUser deliveryUser);

    Optional<DeliveryUser> findHubDeliveryUser(@Param("status") DeliveryStatus deliveryStatus);

    Optional<DeliveryUser> findByDeliveryUserId(Long deliveryUserId);

    List<DeliveryUser> findAllByOrderByDeliveryOrderAsc();

    List<DeliveryUser> findAllByDeliveryTypeOrderByDeliveryOrderAsc(DeliveryType deliveryType);
}
