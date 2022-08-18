package com.jk.cashregister.service.mapper;

import com.jk.cashregister.domain.OrderItem;
import com.jk.cashregister.service.dto.OrderItemDTO;
import com.jk.cashregister.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemDTOMapper {
		private final StockService stockService;

		public OrderItem mapToOrderItem(OrderItemDTO orderItemDTO) {
				OrderItem orderItem = new OrderItem();
				orderItem.setQuantityOrdered(orderItemDTO.getQuantityOrdered());
				return orderItem;
		}

		public OrderItemDTO mapToOrderItemDTO(OrderItem orderItem) {
				OrderItemDTO orderItemDTO = new OrderItemDTO();
				orderItemDTO.setStockId(orderItem.getStock().getId());
				orderItemDTO.setQuantityOrdered(orderItem.getQuantityOrdered());
				return orderItemDTO;
		}
}
