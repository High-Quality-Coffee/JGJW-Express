package com.zgzg.product.application.service;

import com.zgzg.common.exception.BaseException;
import com.zgzg.common.response.ApiResponseData;
import com.zgzg.common.response.Code;
import com.zgzg.common.security.CustomUserDetails;
import com.zgzg.product.application.dto.ProductResponseDTO;
import com.zgzg.product.domain.model.Product;
import com.zgzg.product.domain.repository.ProductRepository;
import com.zgzg.product.presentation.request.ProductRequestDTO;
import com.zgzg.product.presentation.request.ProductStockRequestDTO;
import com.zgzg.product.presentation.request.ProductUpdateRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ApiResponseData<String> create(ProductRequestDTO productRequestDTO){

        //이미 상품이 존재 한다면, 신규 상품 등록은 불가능
        if(productRepository.existsProduct(productRequestDTO.getProductName(),productRequestDTO.getStoreId(),productRequestDTO.getHubId())){
            return ApiResponseData.failure(Code.PRODUCT_ALREADY_EXISTS.getCode(), Code.PRODUCT_ALREADY_EXISTS.getMessage());
        }

        Product product = toProductEntity(productRequestDTO);

        productRepository.save(product).orElseThrow(()->new BaseException(Code.PRODUCT_NOT_CREATE));
        return ApiResponseData.of(Code.PRODUCT_CREATE.getCode(), Code.PRODUCT_CREATE.getMessage(), null);
    }

    public List<ProductResponseDTO> read(){
        List<Product> products = productRepository.findAll();
        List<ProductResponseDTO> productResponseDTOS = new ArrayList<>();
        for(Product product : products){
            productResponseDTOS.add(toProductDTO(product));
        }
        return productResponseDTOS;
    }


    public ProductResponseDTO readOne(UUID id){
        Product product = productRepository.findById(id).orElseThrow(()-> new BaseException(Code.PRODUCT_NOT_EXISTS));
        //삭제된 상품은 조회 불가
        if (product.getDeletedBy()!=null){
            throw new BaseException(Code.PRODUCT_NOT_EXISTS);
        }
        return toProductDTO(product);
    }

    public Page<ProductResponseDTO> search(String name, String sortBy, int page, int size) {
        // 페이지 크기 제한
        int pageSize = switch (size) {
            case 30 -> 30;
            case 50 -> 50;
            default -> 10;
        };

        // 정렬 조건 설정
        Sort sort = Sort.by(
                "createdDateTime".equals(sortBy) ? "createdDateTime" : "modifiedDateTime"
        ).descending();

        Pageable pageable = PageRequest.of(page, pageSize, sort);

        Page<Product> products = productRepository.searchProducts(
                name != null ? name : "",
                pageable
        );

        // Product -> ProductResponseDTO 변환
        return products.map(this::toProductDTO);

    }

    //상품 수정 (상품 이름, 재고 수량만 변경 가능 - 잘못 입력하였을 경우를 대비)
    @Transactional
    public ApiResponseData<String> update(UUID id, ProductUpdateRequestDTO productUpdateRequestDTO){
        Product product = productRepository.findById(id).orElseThrow(()->new BaseException(Code.PRODUCT_NOT_EXISTS));
        product.setProductName(productUpdateRequestDTO.getProductName());
        product.setProductStock(productUpdateRequestDTO.getProductStock());
        productRepository.save(product).orElseThrow(()->new BaseException(Code.PRODUCT_NOT_UPDATE));
        return ApiResponseData.of(Code.PRODUCT_UPDATE.getCode(), Code.PRODUCT_UPDATE.getMessage(), null);
    }

    //상품 삭제
    @Transactional
    public ApiResponseData<String> delete(UUID id, CustomUserDetails customUserDetails) {
        Product product = productRepository.findById(id).orElseThrow(()->new BaseException(Code.PRODUCT_EXISTS));
        product.softDelete(customUserDetails.getUsername());
        productRepository.save(product).orElseThrow(()->new BaseException(Code.PRODUCT_NOT_DELETE));
        return ApiResponseData.of(Code.PRODUCT_DELETE.getCode(), Code.PRODUCT_DELETE.getMessage(),null );
    }

    //상품 재고 추가
    @Transactional
    public ApiResponseData<String> addProduct(ProductStockRequestDTO productStockRequestDTO){
        //추가할 상품 재고는 1개 이상이어야 한다
        if(productStockRequestDTO.getProductStock()<=0){
            throw new BaseException(Code.PRODUCT_STOCK_NOT_ADD);
        }
        //상품이 존재하지 않으면, 신규 등록이 필요, 재고 추가는 안됨
        Product product = productRepository.findById(productStockRequestDTO.getId()).orElseThrow(()->new BaseException(Code.PRODUCT_NOT_EXISTS));
        Integer totalStock = product.getProductStock() + productStockRequestDTO.getProductStock();
        product.setProductStock(totalStock);
        productRepository.save(product);
        return ApiResponseData.of(Code.PRODUCT_UPDATE.getCode(), Code.PRODUCT_UPDATE.getMessage(), null);
    }

    //상품 재고 감소
    @Transactional
    public ApiResponseData<String> reduceProduct(ProductStockRequestDTO productStockRequestDTO){
        //감소할 상품 재고는 1개 이상이어야 한다
        if(productStockRequestDTO.getProductStock()<=0){
            throw new BaseException(Code.PRODUCT_STOCK_NOT_REDUCE);
        }

        //상품이 존재하지 않으면, 신규 등록이 필요, 재고 감소는 안됨
        Product product = productRepository.findById(productStockRequestDTO.getId()).orElseThrow(()->new BaseException(Code.PRODUCT_NOT_EXISTS));

        //재고 감소하기 전, 상품 재고
        Integer productStockNow = product.getProductStock();

        //재고 감소 후, 상품 재고
        Integer productReduceStockNow = productStockNow-productStockRequestDTO.getProductStock();

        //전체 재고는 항상 최소 0 이상을 유지 해야 한다.
        if(productStockNow<=0){
            throw new BaseException(Code.PRODUCT_NO_STOCK);
        }

        //재고 감소 후, 상품재고는 0 이상이어야 한다
        if(productReduceStockNow<0){
            throw new BaseException(Code.PRODUCT_NO_STOCK);
        }

        product.setProductStock(productReduceStockNow);
        productRepository.save(product);
        return ApiResponseData.of(Code.PRODUCT_UPDATE.getCode(),Code.PRODUCT_UPDATE.getMessage(),null );
    }

    public Product toProductEntity(ProductRequestDTO productRequestDTO){
        return Product.builder()
                .productName(productRequestDTO.getProductName())
                .productStock(productRequestDTO.getProductStock())
                .storeId(productRequestDTO.getStoreId())
                .hubId(productRequestDTO.getHubId())
                .build();
    }

    public ProductResponseDTO toProductDTO(Product product){
        return ProductResponseDTO.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productStock(product.getProductStock())
                .storeId(product.getStoreId())
                .hubId(product.getHubId())
                .build();
    }


}
