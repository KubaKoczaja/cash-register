package com.jk.cashregister.service;

import com.jk.cashregister.domain.OrderItem;
import com.jk.cashregister.domain.dto.OrderItemCreateRequest;
import com.jk.cashregister.repository.OrderItemRepository;
import com.jk.cashregister.service.mapper.OrderItemCreateRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {
		private final OrderItemCreateRequestMapper mapper;
		private final OrderItemRepository repository;
//		private final StockRepository stockRepository;

		public void createNewOrderItem(OrderItemCreateRequest request) {
				OrderItem orderItem = mapper.map(request);
				repository.save(orderItem);
				//TODO update quantity value in stock entity
//				stockRepository.save(orderItem.getStock());
		}

		public List<OrderItem> getAllOrderItemsByOrderId(Long orderId) {
				return repository.findAllByOrderId(orderId);
		}
}
