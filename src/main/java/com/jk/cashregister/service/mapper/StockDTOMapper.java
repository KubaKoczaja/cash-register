package com.jk.cashregister.service.mapper;

import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.domain.dto.StockDTO;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class StockDTOMapper {

		public Stock mapToStock(StockDTO request) {
				Stock stock = new Stock();
				stock.setProductCode(request.getProductCode());
				stock.setProductName(request.getProductName());
				stock.setQuantity(request.getQuantity());
				stock.setPrice(request.getPrice());
				return stock;
		}
}
