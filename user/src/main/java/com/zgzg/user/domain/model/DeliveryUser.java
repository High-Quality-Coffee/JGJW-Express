package com.zgzg.user.domain.model;

import com.zgzg.common.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Builder
@Table(name="p_delivery_user")
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long deliveryUserId;

    private UUID hubId;

    private String deliverySlackUsername;

    @Column(nullable = false)
    private DeliveryType deliveryType;

    @Column(nullable = false)
    private Long deliveryOrder;

    @Column(nullable = false)
    private DeliveryStatus deliveryStatus;

}
