package com.jk.cashregister.service;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.OrderItem;
import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.domain.User;
import com.jk.cashregister.repository.OrderRepository;
import com.jk.cashregister.service.exception.NoSuchItemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
		@InjectMocks
		private OrderService orderService;
		@Mock
		private OrderRepository orderRepository;
		@Mock
		private OrderItemService orderItemService;
		private Order orderTest;
		private Order orderTestWithItems;


		@BeforeEach
		void setUp() {
				User user = new User(1L, "testName","testLastName", "CASHIER", "testUserName","test", new ArrayList<>(), new ArrayList<>());
				orderTest = new Order(1L, LocalDateTime.now(), LocalDateTime.now(), user, new ArrayList<>());
				List<OrderItem> orderItemList = List
								.of(new OrderItem(1L, new Stock(), 1,orderTestWithItems), new OrderItem(2L, new Stock(), 1,orderTestWithItems));
				orderTestWithItems = new Order(1L, LocalDateTime.now(), LocalDateTime.now(), user, orderItemList);
		}

		@Test
		void shouldReturnOrderById() {
				when(orderRepository.findById(anyLong())).thenReturn(Optional.of(orderTest));
				Order result = orderService.getOrderById(1L);
				assertEquals(orderTest.getId(), result.getId());
				assertEquals(orderTest.getUser(), result.getUser());
		}

		@Test
		void shouldThrowExceptionWhenOrderDoesNotExists() {
				when(orderRepository.findById(anyLong())).thenThrow(NoSuchItemException.class);
				assertThrows(NoSuchItemException.class, () -> orderService.getOrderById(1L));
		}

		@Test
		void shouldDeleteOrderWithoutItemsById() {
				when(orderRepository.findById(anyLong())).thenReturn(Optional.of(orderTest));
				orderService.deleteOrderWithItems(anyLong());
				verifyNoInteractions(orderItemService);
				verify(orderRepository, times(1)).deleteById(anyLong());
		}

		@Test
		void shouldDeleteOrderByIdAndAllItems() {
				when(orderRepository.findById(anyLong())).thenReturn(Optional.of(orderTestWithItems));
				orderService.deleteOrderWithItems(anyLong());
				verify(orderItemService, times(2)).deleteOrderItemFromOrder(anyLong());
				verify(orderRepository).deleteById(anyLong());
		}
}