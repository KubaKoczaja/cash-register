package com.jk.cashregister.service;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.repository.OrderRepository;
import com.jk.cashregister.service.exception.NoSuchOrderException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
		private final OrderRepository orderRepository;

		public Page<Order> getAllOrders(int page, int size) {
				return orderRepository.findAll(PageRequest.of(page - 1, size));
		}

		public Order getOrderById(Long id) {
				return orderRepository.findById(id).orElseThrow(NoSuchOrderException::new);
		}
}
