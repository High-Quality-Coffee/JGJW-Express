package com.zgzg.product.presentation.controller;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.common.response.Code;
import com.zgzg.common.security.CustomUserDetails;
import com.zgzg.product.application.service.ProductService;
import com.zgzg.product.application.dto.ProductResponseDTO;
import com.zgzg.product.presentation.request.ProductRequestDTO;
import com.zgzg.product.presentation.request.ProductStockRequestDTO;
import com.zgzg.product.presentation.request.ProductUpdateRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    //상품 신규 등록 (같은 업체, 같은 허브, 같은 상품 이름일 경우, 신규 등록은 불가 -> 재고 추가를 해야함)
    @Secured({"ROLE_MASTER","ROLE_HUB", "ROLE_STORE"})
    @PostMapping("")
    public ResponseEntity<ApiResponseData<String>> create(@RequestBody @Valid ProductRequestDTO productRequestDTO){

        ApiResponseData<String> response = productService.create(productRequestDTO);

        //상품 신규 등록은 201 반환, 신규 등록이 아니면 400 status 반환
        HttpStatus status;
        if (response.getCode().equals(Code.PRODUCT_CREATE.getCode())) {
            status = HttpStatus.CREATED; // 201
        } else {
            status = HttpStatus.BAD_REQUEST; // 400
        }

        return ResponseEntity.status(status).body(response);
    }

    //상품 전체 조회
    @Secured({"ROLE_MASTER","ROLE_HUB", "ROLE_STORE", "ROLE_DELIVERY"})
    @GetMapping("")
    public ResponseEntity<ApiResponseData<List<ProductResponseDTO>>> read(){
        return ResponseEntity.ok().body(ApiResponseData.of(Code.PRODUCT_EXISTS.getCode(), Code.PRODUCT_EXISTS.getMessage(),productService.read()));
    }

    //특정 상품 조회
    @Secured({"ROLE_MASTER","ROLE_HUB", "ROLE_STORE", "ROLE_DELIVERY"})
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseData<ProductResponseDTO>> read_one(@PathVariable("id") UUID id){
        return ResponseEntity.ok().body(ApiResponseData.of(Code.PRODUCT_EXISTS.getCode(), Code.PRODUCT_EXISTS.getMessage(),productService.readOne(id)));
    }

    //상품 검색
    @Secured({"ROLE_MASTER","ROLE_HUB", "ROLE_STORE", "ROLE_DELIVERY"})
    @GetMapping("/search")
    public ResponseEntity<ApiResponseData<List<ProductResponseDTO>>> searchProducts(
            @RequestParam(name="name", required = false, defaultValue = "") String name,
            @RequestParam(name="sortBy", required = false, defaultValue = "createdDateTime") String sortBy,
            @RequestParam(name="page", required = false, defaultValue = "0") int page,
            @RequestParam(name="size", required = false, defaultValue = "10") int size
    ) {
        Page<ProductResponseDTO> products = productService.search(name, sortBy, page, size);
        return ResponseEntity.ok().body(ApiResponseData.of(Code.PRODUCT_EXISTS.getCode(), Code.PRODUCT_EXISTS.getMessage(), products.getContent()));
    }

    //특정 상품 수정
    @Secured({"ROLE_MASTER","ROLE_HUB", "ROLE_STORE"})
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseData<String>> update(@PathVariable("id") UUID id, @RequestBody ProductUpdateRequestDTO productUpdateRequestDTO){
        return ResponseEntity.ok().body(productService.update(id,productUpdateRequestDTO));

    }

    //특정 상품 삭제
    @Secured({"ROLE_MASTER","ROLE_HUB"})
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseData<String>> delete(@PathVariable("id") UUID id, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        return ResponseEntity.ok().body(productService.delete(id, customUserDetails));
    }

    //재고 추가
    @Secured({"ROLE_MASTER","ROLE_HUB", "ROLE_STORE"})
    @PutMapping("/add")
    public ResponseEntity<ApiResponseData<String>> addProduct(@RequestBody ProductStockRequestDTO productStockRequestDTO){
        return ResponseEntity.ok().body(productService.addProduct(productStockRequestDTO));
    }

    //재고 감소
    @Secured({"ROLE_MASTER","ROLE_HUB", "ROLE_STORE"})
    @PutMapping("/reduce")
    public ResponseEntity<ApiResponseData<String>> reduceProduct(@RequestBody ProductStockRequestDTO productStockRequestDTO){
        return ResponseEntity.ok().body(productService.reduceProduct(productStockRequestDTO));
    }

}
