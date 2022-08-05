package com.jk.cashregister.service;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.dto.OrderDTO;
import com.jk.cashregister.repository.OrderRepository;
import com.jk.cashregister.service.mapper.OrderDTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderWorkflowServiceTest {
		@Mock
		private OrderDTOMapper orderDTOMapper;

		@Mock
		private OrderRepository orderRepository;

		@InjectMocks
		private OrderWorkflowService orderWorkflowService;
		private Order order;
		private OrderDTO orderDTO;
		@BeforeEach
		void setUp() {
				orderDTO = new OrderDTO(LocalDateTime.now(),1L);
				order = new Order();
//				when(orderDTOMapper.map(orderDTO)).thenReturn(order);
//				when(orderRepository.save(order)).thenReturn(new Order());
		}

		@Test
		void openNewOrderTest() {
				ArgumentCaptor<OrderDTOMapper> ac = ArgumentCaptor.forClass(OrderDTOMapper.class);
//				when(orderRepository.save(order)).thenReturn(new Order());
				orderWorkflowService.openNewOrder(orderDTO);
				verify(orderDTOMapper).map(orderDTO);


		}

		@Test
		void createNewOrderItem() {
		}

		@Test
		void closeNewOrder() {
		}
}