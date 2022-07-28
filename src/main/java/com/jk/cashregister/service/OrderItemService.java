package com.jk.cashregister.service;

import com.jk.cashregister.domain.OrderItem;
import com.jk.cashregister.domain.dto.OrderItemCreateRequest;
import com.jk.cashregister.repository.OrderItemRepository;
import com.jk.cashregister.service.mapper.OrderItemCreateRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {
		private final OrderItemCreateRequestMapper mapper;
		private final OrderItemRepository repository;
		private final StockService stockService;

		@Transactional
		public void createNewOrderItem(OrderItemCreateRequest request) {
				OrderItem orderItem = mapper.map(request);
				repository.save(orderItem);
				// stock quantity is updated. check if there is enough quantity in warehouse -> stockService updateQuantity method
				// stock is updated after creating each order item
				stockService.updateQuantity(orderItem.getOrder().getId(), orderItem.getQuantityOrdered());
		}

		public List<OrderItem> getAllOrderItemsByOrderId(Long orderId) {
				return repository.findAllByOrderId(orderId);
		}
}
