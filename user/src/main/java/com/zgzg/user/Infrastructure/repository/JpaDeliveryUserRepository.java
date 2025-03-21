package com.zgzg.user.Infrastructure.repository;

import com.zgzg.user.domain.model.DeliveryUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDeliveryUserRepository extends JpaRepository<DeliveryUser, Long> {
}
