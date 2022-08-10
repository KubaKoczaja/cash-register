package com.jk.cashregister.service;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.repository.OrderRepository;
import com.jk.cashregister.service.exception.NoSuchItemException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
		private final OrderRepository orderRepository;
		private final OrderItemService orderItemService;

		public Page<Order> getAllOrders(int page, int size) {
				return orderRepository.findAll(PageRequest.of(page - 1, size));
		}

		public Order getOrderById(Long id) {
				return orderRepository.findById(id).orElseThrow(() -> new NoSuchItemException("Order with such id doesn't exist"));
		}

		@Transactional
		public void deleteOrderWithItems(Long id) {
				Order orderById = getOrderById(id);
				orderById.getOrderItemList()
								.forEach(o -> orderItemService.deleteOrderItemFromOrder(o.getId()));
				orderRepository.deleteById(id);
		}
}
