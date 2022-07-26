package com.jk.cashregister.service;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.OrderItem;
import com.jk.cashregister.domain.dto.OrderItemCreateRequest;
import com.jk.cashregister.repository.OrderItemRepository;
import com.jk.cashregister.repository.StockRepository;
import com.jk.cashregister.service.mapper.OrderItemCreateRequestMapper;
import com.jk.cashregister.service.validator.OrderItemCreateRequestValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderItemService {
		private final OrderItemCreateRequestValidator validator;
		private final OrderItemCreateRequestMapper mapper;
		private final OrderItemRepository repository;
		private final StockRepository stockRepository;
		private final OrderItemRepository orderItemRepository;

		public OrderItemService(OrderItemCreateRequestValidator validator, OrderItemCreateRequestMapper mapper,
														OrderItemRepository repository, StockRepository stockRepository,
														OrderItemRepository orderItemRepository) {
				this.validator = validator;
				this.mapper = mapper;
				this.repository = repository;
				this.stockRepository = stockRepository;
				this.orderItemRepository = orderItemRepository;
		}

		@Transactional(rollbackFor = RuntimeException.class)
		public void createNewOrderItem(OrderItemCreateRequest request) {
				validator.validate(request);
				OrderItem orderItem = mapper.map(request);
				repository.save(orderItem);
				stockRepository.save(orderItem.getStock());
		}

		public List<OrderItem> getAllOrderItemsByOrderId(Long orderId) {
				return orderItemRepository.findAllByOrderId(orderId);
		}
}
