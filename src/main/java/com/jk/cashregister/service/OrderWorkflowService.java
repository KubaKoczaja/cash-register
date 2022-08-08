package com.jk.cashregister.service;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.OrderItem;
import com.jk.cashregister.domain.dto.OrderDTO;
import com.jk.cashregister.repository.OrderItemRepository;
import com.jk.cashregister.repository.OrderRepository;
import com.jk.cashregister.service.exception.EmptyOrderException;
import com.jk.cashregister.service.mapper.OrderDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderWorkflowService {
		private final OrderItemRepository orderItemRepository;
		private final OrderRepository orderRepository;
		private final OrderDTOMapper orderDTOMapper;
		private final StockService stockService;
		private final OrderService orderService;

		public Order openNewOrder(OrderDTO orderDTO) {
				Order openOrder = orderDTOMapper.map(orderDTO);
				return orderRepository.save(openOrder);
		}

		@Transactional
		public void addNewOrderItemToOrder(OrderItem orderItem) {
				OrderItem savedOrderItem = orderItemRepository.save(orderItem);

				// stock quantity is updated. check if there is enough quantity in warehouse -> stockService updateQuantity method
				// stock is updated after creating each order item

				stockService.updateQuantity(savedOrderItem.getStock().getId(), savedOrderItem.getQuantityOrdered());
		}

		public void closeNewOrder(Long id) {
				Order order = orderService.getOrderById(id);
				if (order.getOrderItemList().isEmpty()) {
						orderRepository.deleteById(id);
						throw new EmptyOrderException();
				}
				order.setCloseDate(LocalDateTime.now());
				orderRepository.save(order);
		}
}
