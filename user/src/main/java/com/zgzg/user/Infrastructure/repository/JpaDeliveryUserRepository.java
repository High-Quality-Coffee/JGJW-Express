package com.zgzg.user.Infrastructure.repository;

import com.zgzg.user.domain.model.DeliveryStatus;
import com.zgzg.user.domain.model.DeliveryType;
import com.zgzg.user.domain.model.DeliveryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaDeliveryUserRepository extends JpaRepository<DeliveryUser, Long> {
    @Query("SELECT du FROM DeliveryUser du WHERE du.deliveryStatus = :status ORDER BY du.deliveryOrder ASC")
    Optional<DeliveryUser> findHubDeliveryUser(@Param("status") DeliveryStatus deliveryStatus);

    Optional<DeliveryUser> findByDeliveryUserId(Long deliveryUserId);

    List<DeliveryUser> findAllByOrderByDeliveryOrderAsc();

    List<DeliveryUser> findAllByDeliveryTypeOrderByDeliveryOrderAsc(DeliveryType deliveryType);

    @Query("SELECT su FROM DeliveryUser su WHERE su.hubId = :hubId AND su.deliveryType = :deliveryType ORDER BY su.deliveryOrder ASC")
    List<DeliveryUser> findStoreDeliveryUser(@Param("hubId")UUID id, @Param("deliveryType") DeliveryType deliveryType);
}
