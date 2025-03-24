package com.zgzg.user.domain.model;

import com.zgzg.common.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

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

    @Setter
    private UUID hubId;

    private String deliverySlackUsername;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Setter
    private DeliveryType deliveryType;

    @Column(nullable = false)
    private Long deliveryOrder;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Setter
    private DeliveryStatus deliveryStatus;

}
