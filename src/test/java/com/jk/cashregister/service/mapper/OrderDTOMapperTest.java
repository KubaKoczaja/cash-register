package com.jk.cashregister.service.mapper;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.User;
import com.jk.cashregister.domain.dto.OrderDTO;
import com.jk.cashregister.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderDTOMapperTest {
		@InjectMocks
		private OrderDTOMapper orderDTOMapper;
		@Mock
		private UserService userService;

		@Test
		void map() {
				OrderDTO orderDTOInput = new OrderDTO(LocalDateTime.now());
				when(userService
								.getAuthenticatedUser())
								.thenReturn(new User(1L, "a","a", "a","a", "a", new ArrayList<>(), new ArrayList<>()));
				Order result = orderDTOMapper.map(orderDTOInput);
				assertEquals(orderDTOInput.getOpenDate(), result.getOpenDate());
				assertEquals(1L, result.getUser().getId());
		}
}