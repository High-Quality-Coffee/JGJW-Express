package com.zgzg.order.application.client;

import java.util.List;

import com.zgzg.order.application.dto.res.OrderDetailDTO;
import com.zgzg.order.domain.entity.OrderDetail;

public interface ProductClient {
	// 상품 조회
	boolean getProduct(List<OrderDetailDTO> productList);

	// 재고 차감
	boolean reduceProduct(List<OrderDetailDTO> productList);

	// 재고 추가
	boolean increaseProduct(List<OrderDetail> productList);
}
