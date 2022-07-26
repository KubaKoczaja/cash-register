package com.jk.cashregister.service.mapper;

import com.jk.cashregister.domain.OrderItem;
import com.jk.cashregister.domain.dto.OrderItemCreateRequest;
import com.jk.cashregister.service.OrderService;
import com.jk.cashregister.service.StockService;
import org.springframework.stereotype.Component;

@Component
public class OrderItemCreateRequestMapper {
		private final OrderService orderService;
		private final StockService stockService;

		public OrderItemCreateRequestMapper(OrderService orderService, StockService stockService) {
				this.orderService = orderService;
				this.stockService = stockService;
		}

		public OrderItem map(OrderItemCreateRequest orderItemCreateRequest) {
				OrderItem orderItem = new OrderItem();
				orderItem.setOrder(orderService.getOrderById(orderItemCreateRequest.getOrderId()));
				orderItem.setStock(stockService.getById(orderItemCreateRequest.getStockId()));
				orderItem.setQuantityOrdered(orderItemCreateRequest.getQuantityOrdered());
				return orderItem;
		}
}
