package com.zgzg.product.presentation.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductRequestDTO {

    private String productName;
    private Integer productStock;
    private UUID storeId;
    private UUID hubId;

}
