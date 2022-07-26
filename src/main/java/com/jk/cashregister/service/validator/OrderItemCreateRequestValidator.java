package com.jk.cashregister.service.validator;

import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.domain.dto.OrderItemCreateRequest;
import com.jk.cashregister.service.StockService;
import com.jk.cashregister.service.exception.NotEnoughQuantityException;
import org.springframework.stereotype.Component;

@Component
public class OrderItemCreateRequestValidator {
		private final StockService stockService;

		public OrderItemCreateRequestValidator(StockService stockService) {
				this.stockService = stockService;
		}

		public void validate(OrderItemCreateRequest orderItemCreateRequest) {
				Stock stock = stockService.getById(orderItemCreateRequest.getStockId());
				if (stock.getQuantity() < orderItemCreateRequest.getQuantityOrdered()) {
						throw new NotEnoughQuantityException();
				}
		}
}
