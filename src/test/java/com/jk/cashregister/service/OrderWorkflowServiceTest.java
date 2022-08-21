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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderWorkflowServiceTest {
		@Mock
		private OrderDTOMapper orderDTOMapper;
		@Mock
		private OrderItemDTOMapper orderItemDTOMapper;
		@Mock
		private OrderRepository orderRepository;
		@Mock
		private OrderItemRepository orderItemRepository;
		@Mock
		private OrderService orderService;
		@Mock
		private StockService stockService;
		@Mock
		private UserService userService;
		@InjectMocks
		private OrderWorkflowService orderWorkflowService;
		private Order newOrder;
		private OrderDTO orderDTOInput;
		private OrderItem orderItemInput;
		private OrderItem orderItemToSave;
		private Stock stockTest;
		private ArgumentCaptor<Order> orderArgumentCaptor;
		private ArgumentCaptor<OrderItem> orderItemArgumentCaptor;

		@BeforeEach
		void setUp() {
				stockTest = new Stock(1L, "aaa", "test", 10, 100, new ArrayList<>());
				orderDTOInput = new OrderDTO(LocalDateTime.now());
				orderItemInput = new OrderItem(1L, stockTest, 10, new Order());
				orderItemToSave = new OrderItem(1L, stockTest, 10, newOrder);
				newOrder = new Order(1L, LocalDateTime.now(), null, new User(), new ArrayList<>());

				orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
				orderItemArgumentCaptor = ArgumentCaptor.forClass(OrderItem.class);
		}

		@Test
		void shouldOpenNewOrder() {
				when(userService.getAuthenticatedUser()).thenReturn(new User());
				when(orderDTOMapper.map(any(OrderDTO.class), any(User.class))).thenReturn(newOrder);
				orderWorkflowService.openNewOrder(orderDTOInput);

				verify(orderRepository).save(orderArgumentCaptor.capture());
				Order result = orderArgumentCaptor.getValue();

				assertEquals(orderDTOInput.getOpenDate(), result.getOpenDate());
				assertNotNull(result.getUser());
		}

		@Test
		void shouldAddNewOrderItemToExistingOrder() {
				OrderItemDTO orderItemDTOInput = new OrderItemDTO(1L, 10);
				when(orderService.getOrderById(anyLong())).thenReturn(newOrder);
				when(stockService.getStockById(anyLong())).thenReturn(stockTest);
				when(orderItemDTOMapper.mapToOrderItem(any(OrderItemDTO.class), any(Order.class), any(Stock.class))).thenReturn(orderItemInput);
				when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItemToSave);
				Order result = orderWorkflowService.addNewOrderItemToOrder(orderItemDTOInput, 1L);
				verify(stockService).updateQuantity(anyLong(), anyInt());

		}
		@Test
		void shouldThrowExceptionWhenClosingOrderWithoutAnyOrderItems() {
				when(orderService.getOrderById(anyLong())).thenReturn(newOrder);
				assertThrows(EmptyOrderException.class, () -> orderWorkflowService.closeNewOrder(1L));
		}

		@Test
		void shouldCloseOrderWithItems() {
				newOrder.setOrderItemList(List.of(orderItemInput));
				when(orderService.getOrderById(anyLong())).thenReturn(newOrder);

				orderWorkflowService.closeNewOrder(1L);

				verify(orderRepository).save(orderArgumentCaptor.capture());
				Order result = orderArgumentCaptor.getValue();

				assertEquals(newOrder.getOrderItemList(), result.getOrderItemList());
				assertNotNull(result.getCloseDate());
		}
}