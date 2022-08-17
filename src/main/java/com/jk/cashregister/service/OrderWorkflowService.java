package com.jk.cashregister.service;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.OrderItem;
import com.jk.cashregister.domain.dto.OrderDTO;
import com.jk.cashregister.domain.dto.OrderItemDTO;
import com.jk.cashregister.repository.OrderItemRepository;
import com.jk.cashregister.repository.OrderRepository;
import com.jk.cashregister.service.exception.EmptyOrderException;
import com.jk.cashregister.service.mapper.OrderDTOMapper;
import com.jk.cashregister.service.mapper.OrderItemDTOMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderWorkflowService {
		private final OrderItemDTOMapper orderItemDTOMapper;
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
		public Order addNewOrderItemToOrder(OrderItemDTO orderItemDTO, Long orderId) {
				Order order = orderService.getOrderById(orderId);
				OrderItem orderItem = orderItemDTOMapper.mapToOrderItem(orderItemDTO);
				orderItem.setOrder(order);
				OrderItem savedOrderItem = orderItemRepository.save(orderItem);
				stockService.updateQuantity(savedOrderItem.getStock().getId(), savedOrderItem.getQuantityOrdered());
				return order;
		}

		public void closeNewOrder(Long id) {
				Order order = orderService.getOrderById(id);
				if (order.getOrderItemList().isEmpty()) {
						orderRepository.save(order);
						log.warn("Attempt to create empty order");
						throw new EmptyOrderException("Order can't be empty");
				}
				order.setCloseDate(LocalDateTime.now());
				orderRepository.save(order);
		}
}
