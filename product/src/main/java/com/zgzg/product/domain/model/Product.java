package com.zgzg.product.domain.model;

import com.zgzg.common.utils.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="p_product")
public class Product extends BaseEntity {
    @Id
    @UuidGenerator
    @Column
    private UUID id;

    @Column(nullable = false)
    @Setter
    private String productName;

    @Column(nullable = false)
    @Setter
    private Integer productStock;

    @Column(nullable = false)
    @Setter
    private UUID storeId;

    @Column(nullable = false)
    @Setter
    private UUID hubId;

}
