package com.zgzg.product.presentation.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ProductUpdateRequestDTO {
    private String productName;
    private Integer productStock;
}
