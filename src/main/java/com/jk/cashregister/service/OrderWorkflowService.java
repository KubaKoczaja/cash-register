package com.jk.cashregister.service;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.OrderItem;
import com.jk.cashregister.domain.dto.OrderItemDTO;
import com.jk.cashregister.domain.dto.OrderDTO;
import com.jk.cashregister.repository.OrderItemRepository;
import com.jk.cashregister.repository.OrderRepository;
import com.jk.cashregister.service.exception.EmptyOrderException;
import com.jk.cashregister.service.mapper.OrderItemDTOMapper;
import com.jk.cashregister.service.mapper.OrderDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderWorkflowService {
		private final OrderItemRepository orderItemRepository;
		private final OrderRepository orderRepository;
		private final OrderDTOMapper orderDTOMapper;
		private final OrderItemDTOMapper orderItemDTOMapper;
		private final StockService stockService;
		private final OrderService orderService;

		//		OrderOpenRequest holds information about user id and open of order time
		public Order openNewOrder(OrderDTO orderDTO) {
				Order openOrder = orderDTOMapper.map(orderDTO);
				return orderRepository.save(openOrder);
		}

		@Transactional
		public void createNewOrderItem(OrderItemDTO request) {
				OrderItem orderItem = orderItemDTOMapper.map(request);
				orderItemRepository.save(orderItem);
				// stock quantity is updated. check if there is enough quantity in warehouse -> stockService updateQuantity method
				// stock is updated after creating each order item
				stockService.updateQuantity(orderItem.getOrder().getId(), orderItem.getQuantityOrdered());
		}

		public void closeNewOrder(Long id) {
				Order order = orderService.getOrderById(id);
				List<OrderItem> orderItemsList = orderItemRepository.findAllByOrderId(id);
				if (orderItemsList.isEmpty()) {
						orderRepository.deleteById(id);
						throw new EmptyOrderException();
				}
				order.setOrderItemList(orderItemsList);
				order.setCloseDate(LocalDateTime.now());
				orderRepository.save(order);
		}
}
