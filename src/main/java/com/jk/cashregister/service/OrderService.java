package com.jk.cashregister.service;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.dto.OrderOpenRequest;
import com.jk.cashregister.repository.OrderRepository;
import com.jk.cashregister.repository.UserRepository;
import com.jk.cashregister.service.exception.NoSuchOrderException;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Data
public class OrderService {
		private final OrderRepository orderRepository;
		private final UserRepository userRepository;

		public OrderService(OrderRepository orderRepository1, UserRepository userRepository) {
				this.orderRepository = orderRepository1;
				this.userRepository = userRepository;
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
				openOrder.setUser(userRepository.findById(orderOpenRequest.getUserId()).get());
				return orderRepository.save(openOrder);
		}
}
