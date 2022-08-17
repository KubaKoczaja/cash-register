package com.jk.cashregister.service.mapper;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.OrderItem;
import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.domain.dto.OrderItemDTO;
import com.jk.cashregister.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderItemDTOMapperTest {
		@InjectMocks
		private OrderItemDTOMapper orderItemDTOMapper;
		@Mock
		private StockService stockService;
		private OrderItemDTO orderItemDTOTest;
		private OrderItem orderItem;
		private Stock stock;

		@BeforeEach
		void setUp() {
				stock = new Stock(1L, "aaa", "test", 20, 100, new ArrayList<>());
				orderItemDTOTest = new OrderItemDTO(1L, 10);
				orderItem = new OrderItem(1L, stock, 10, new Order());
		}

		@Test
		void shouldMapOrderItemDTOToOrderItem() {
				when(stockService.getStockById(anyLong())).thenReturn(stock);
				OrderItem result = orderItemDTOMapper.mapToOrderItem(orderItemDTOTest);
				assertEquals(orderItemDTOTest.getQuantityOrdered(),result.getQuantityOrdered());
				assertEquals(orderItemDTOTest.getStockId(), result.getStock().getId());
		}

		@Test
		void shouldMapOrderItemToOrderItemDTO() {
				OrderItemDTO result = orderItemDTOMapper.mapToOrderItemDTO(orderItem);
				assertEquals(orderItem.getQuantityOrdered(), result.getQuantityOrdered());
				assertEquals(orderItem.getStock().getId(),result.getStockId());
		}
}