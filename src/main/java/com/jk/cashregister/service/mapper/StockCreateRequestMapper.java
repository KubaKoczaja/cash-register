package com.jk.cashregister.service.mapper;

import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.domain.dto.StockCreateRequest;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class StockCreateRequestMapper {

		public Stock mapToStock(StockCreateRequest request) {
				Stock stock = new Stock();
				stock.setProductCode(request.getProductCode());
				stock.setProductName(request.getProductName());
				stock.setQuantity(request.getQuantity());
				stock.setPrice(request.getPrice());
				return stock;
		}
}
