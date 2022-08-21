package com.jk.cashregister.service;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.OrderItem;
import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.domain.User;
import com.jk.cashregister.repository.OrderItemRepository;
import com.jk.cashregister.repository.OrderRepository;
import com.jk.cashregister.service.dto.OrderDTO;
import com.jk.cashregister.service.dto.OrderItemDTO;
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
		private final OrderItemRepository orderItemRepository;
		private final OrderRepository orderRepository;
		private final StockService stockService;
		private final OrderService orderService;
		private final UserService userService;
		private final OrderDTOMapper orderDTOMapper;
		private final OrderItemDTOMapper orderItemDTOMapper;
		@Transactional
		public Order openNewOrder(OrderDTO orderDTO) {
				User authUser = userService.getAuthenticatedUser();
				Order openOrder = orderDTOMapper.map(orderDTO, authUser);
				return orderRepository.save(openOrder);
		}

		@Transactional
		public Order addNewOrderItemToOrder(OrderItemDTO orderItemDTO, Long orderId) {
				Order order = orderService.getOrderById(orderId);
				Stock stock = stockService.getStockById(orderItemDTO.getStockId());
				OrderItem orderItem = orderItemDTOMapper.mapToOrderItem(orderItemDTO, order, stock);
				OrderItem savedOrderItem = orderItemRepository.save(orderItem);
				stockService.updateQuantity(savedOrderItem.getStock().getId(), savedOrderItem.getQuantityOrdered());
				return order;
		}

		@Transactional
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
