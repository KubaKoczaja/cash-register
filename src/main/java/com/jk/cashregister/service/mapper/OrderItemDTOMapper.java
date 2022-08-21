package com.jk.cashregister.service.mapper;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.OrderItem;
import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.service.StockService;
import com.jk.cashregister.service.dto.OrderItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemDTOMapper {
		private final StockService stockService;

		public OrderItem mapToOrderItem(OrderItemDTO orderItemDTO, Order order, Stock stock) {
				OrderItem orderItem = new OrderItem();
				orderItem.setQuantityOrdered(orderItemDTO.getQuantityOrdered());
				orderItem.setOrder(order);
				orderItem.setStock(stock);
				return orderItem;
		}

		public OrderItemDTO mapToOrderItemDTO(OrderItem orderItem) {
				OrderItemDTO orderItemDTO = new OrderItemDTO();
				orderItemDTO.setStockId(orderItem.getStock().getId());
				orderItemDTO.setQuantityOrdered(orderItem.getQuantityOrdered());
				return orderItemDTO;
		}
}
