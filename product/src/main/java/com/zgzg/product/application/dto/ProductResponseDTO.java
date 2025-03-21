package com.zgzg.product.application.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ProductResponseDTO {
    private UUID id;
    private String productName;
    private Integer productStock;
    private UUID storeId;
    private UUID hubId;

}
