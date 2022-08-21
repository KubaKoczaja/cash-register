package com.jk.cashregister.service.mapper;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.OrderItem;
import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.domain.User;
import com.jk.cashregister.service.dto.OrderItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class OrderItemDTOMapperTest {
		@InjectMocks
		private OrderItemDTOMapper orderItemDTOMapper;

		private OrderItemDTO orderItemDTOTest;
		private OrderItem orderItem;
		private Stock stock;
		private Order order;

		@BeforeEach
		void setUp() {
				stock = new Stock(1L, "aaa", "test", 20, 100, new ArrayList<>());
				orderItemDTOTest = new OrderItemDTO(1L, 10);
				orderItem = new OrderItem(1L, stock, 10, new Order());
				order = new Order(1L, LocalDateTime.now(), null, new User(), new ArrayList<>());
		}

		@Test
		void shouldMapOrderItemDTOToOrderItem() {

				OrderItem result = orderItemDTOMapper.mapToOrderItem(orderItemDTOTest, order, stock);
				assertEquals(orderItemDTOTest.getQuantityOrdered(),result.getQuantityOrdered());

		}

		@Test
		void shouldMapOrderItemToOrderItemDTO() {
				OrderItemDTO result = orderItemDTOMapper.mapToOrderItemDTO(orderItem);
				assertEquals(orderItem.getQuantityOrdered(), result.getQuantityOrdered());

		}
}