package com.jk.cashregister.service;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.OrderItem;
import com.jk.cashregister.domain.User;
import com.jk.cashregister.domain.dto.OrderOpenRequest;
import com.jk.cashregister.repository.OrderRepository;
import com.jk.cashregister.repository.UserRepository;
import com.jk.cashregister.service.exception.EmptyOrderException;
import com.jk.cashregister.service.exception.NoSuchOrderException;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class OrderService {
		private final OrderRepository orderRepository;
		private final UserRepository userRepository;
		private final OrderItemService orderItemService;

		public OrderService(OrderRepository orderRepository1, UserRepository userRepository,
												OrderItemService orderItemService) {
				this.orderRepository = orderRepository1;
				this.userRepository = userRepository;
				this.orderItemService = orderItemService;
		}

		public List<Order> getAllOrders() {
				return orderRepository.findAll();
		}

		public Order getOrderById(Long id) {
				return orderRepository.findById(id).orElseThrow(NoSuchOrderException::new);
		}

//		OrderOpenRequest holds information about user id and open of order time
		public Order openNewOrder(OrderOpenRequest orderOpenRequest) {
				Order openOrder = new Order();
				openOrder.setOpenDate(LocalDateTime.now());
				Optional<User> user = userRepository.findById(orderOpenRequest.getUserId());
				user.ifPresent(openOrder::setUser);
				return orderRepository.save(openOrder);
		}

		public void closeNewOrder(Long id) {
				Order order = getOrderById(id);
				List<OrderItem> orderItemsList = orderItemService.getAllOrderItemsByOrderId(id);
				if (orderItemsList.isEmpty()) {
						deleteOrderById(id);
						throw new EmptyOrderException();
				}
				order.setOrderItemList(orderItemsList);
				order.setCloseDate(LocalDateTime.now());
				orderRepository.save(order);
		}

		private void deleteOrderById(Long id) {
				orderRepository.deleteById(id);
		}
}
