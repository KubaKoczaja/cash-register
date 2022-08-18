package com.jk.cashregister.service;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.OrderItem;
import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.service.dto.OrderItemDTO;
import com.jk.cashregister.repository.OrderItemRepository;
import com.jk.cashregister.repository.StockRepository;
import com.jk.cashregister.service.exception.NoSuchItemException;
import com.jk.cashregister.service.mapper.OrderItemDTOMapper;
import com.jk.cashregister.service.validator.StockQuantityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceTest {

		@InjectMocks
		private OrderItemService orderItemService;
		@Mock
		private OrderItemRepository orderItemRepository;
		@Mock
		private OrderItemDTOMapper orderItemDTOMapper;
		@Mock
		private StockRepository stockRepository;
		@Mock
		private StockQuantityValidator stockQuantityValidator;
		private OrderItem orderItemInput;
		private Stock stockExpected;

		private OrderItem orderItemExpected;
		private OrderItemDTO orderItemDTOInput;
		private ArgumentCaptor<Stock> stockArgumentCaptor;
		private ArgumentCaptor<OrderItem> orderItemArgumentCaptor;

		@BeforeEach
		void setUp() {
				Stock stockTest = new Stock(1L, "aaa", "test", 10, 100, new ArrayList<>());
				orderItemInput = new OrderItem(1L, stockTest, 20, new Order());

				when(orderItemRepository.findById(anyLong())).thenReturn(Optional.of(orderItemInput));

				stockArgumentCaptor = ArgumentCaptor.forClass(Stock.class);
				orderItemArgumentCaptor = ArgumentCaptor.forClass(OrderItem.class);

				stockExpected = new Stock(1L,"aaa","test",8,100,new ArrayList<>());
				orderItemExpected = new OrderItem(1L, stockTest, 22, new Order());
				orderItemDTOInput = new OrderItemDTO(1L, 22);
		}

		@Test
		void shouldReturnOrderItemById() {
				OrderItem result = orderItemService.getOrderItemById(anyLong());
				assertEquals(orderItemInput.getQuantityOrdered(), result.getQuantityOrdered());
				assertEquals(orderItemInput.getStock(), result.getStock());
		}

		@Test
		void shouldThrowExceptionWhenNoOrderItemExists() {
				when(orderItemRepository.findById(anyLong())).thenThrow(NoSuchItemException.class);
				assertThrows(NoSuchItemException.class, () -> orderItemService.getOrderItemById(1L));
		}

		@Test
		void shouldGetOrderItemDTOById() {
				orderItemDTOInput.setQuantityOrdered(20);
				when(orderItemDTOMapper.mapToOrderItemDTO(Mockito.any(OrderItem.class))).thenReturn(orderItemDTOInput);

				OrderItemDTO res = orderItemService.getOrderItemDTOById(anyLong());

				assertEquals(orderItemInput.getQuantityOrdered(), res.getQuantityOrdered());
				assertEquals(orderItemInput.getStock().getId(), res.getStockId());
		}

		@Test
		void shouldUpdateOrderItemQuantityFrom20To22AndUpdateStockQuantityFrom10To8() {
				when(stockQuantityValidator.validateOrderedQuantity(anyInt(),anyInt())).thenReturn(true);
				orderItemService.updateOrderItem(anyLong(), orderItemDTOInput);

				verify(stockRepository).save(stockArgumentCaptor.capture());
				Stock resultStock = stockArgumentCaptor.getValue();
				assertEquals(stockExpected.getQuantity(), resultStock.getQuantity());

				verify(orderItemRepository).save(orderItemArgumentCaptor.capture());
				OrderItem resultOrderItem = orderItemArgumentCaptor.getValue();
				assertEquals(orderItemExpected.getQuantityOrdered(), resultOrderItem.getQuantityOrdered());
		}

		@Test
		void shouldDeleteOrderItemFromOrderAndUpdateStockQuantityTo30() {
				stockExpected.setQuantity(30);
				orderItemService.deleteOrderItemFromOrder(anyLong());

				verify(stockRepository).save(stockArgumentCaptor.capture());
				Stock resultStock = stockArgumentCaptor.getValue();
				assertEquals(stockExpected.getQuantity(), resultStock.getQuantity());

				verify(orderItemRepository).deleteById(anyLong());
		}
}