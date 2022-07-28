package com.jk.cashregister.service;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.dto.OrderOpenRequest;
import com.jk.cashregister.repository.OrderRepository;
import com.jk.cashregister.service.exception.NoSuchOrderException;
import com.jk.cashregister.service.mapper.OrderOpenRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
		private final OrderRepository orderRepository;
		private final OrderOpenRequestMapper mapper;

//		private final OrderItemService orderItemService;


		public List<Order> getAllOrders(int page) {
				Page<Order> all = orderRepository.findAll(PageRequest.of(page, 5));
				return all.getContent();
		}

		public Order getOrderById(Long id) {
				return orderRepository.findById(id).orElseThrow(NoSuchOrderException::new);
		}

//		OrderOpenRequest holds information about user id and open of order time
		public Order openNewOrder(OrderOpenRequest orderOpenRequest) {
				Order openOrder = mapper.map(orderOpenRequest);
				return orderRepository.save(openOrder);
		}

		public void closeNewOrder(Long id) {
//				Order order = getOrderById(id);
//				List<OrderItem> orderItemsList = orderItemService.getAllOrderItemsByOrderId(id);
//				if (orderItemsList.isEmpty()) {
//						deleteOrderById(id);
//						throw new EmptyOrderException();
//				}
//				order.setOrderItemList(orderItemsList);
//				order.setCloseDate(LocalDateTime.now());
//				orderRepository.save(order);
		}

		private void deleteOrderById(Long id) {
				orderRepository.deleteById(id);
		}
}
