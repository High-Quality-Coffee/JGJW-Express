package com.zgzg.product.presentation.request;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ProductStockRequestDTO {
    private UUID id;
    private Integer productStock;
}
