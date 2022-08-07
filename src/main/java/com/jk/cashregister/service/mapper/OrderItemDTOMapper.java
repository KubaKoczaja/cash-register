package com.jk.cashregister.service.mapper;

import com.jk.cashregister.domain.OrderItem;
import com.jk.cashregister.domain.dto.OrderItemDTO;
import com.jk.cashregister.service.OrderService;
import com.jk.cashregister.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemDTOMapper {
		private final OrderService orderService;
		private final StockService stockService;

		public OrderItem map(OrderItemDTO orderItemDTO) {
				OrderItem orderItem = new OrderItem();
//				orderItem.setOrder(orderService.getOrderById(orderItemDTO.getOrderId()));
				orderItem.setStock(stockService.getById(orderItemDTO.getStockId()));
				orderItem.setQuantityOrdered(orderItemDTO.getQuantityOrdered());
				return orderItem;
		}
}
