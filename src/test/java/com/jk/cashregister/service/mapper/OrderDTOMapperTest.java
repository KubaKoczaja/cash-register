package com.jk.cashregister.service.mapper;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.User;
import com.jk.cashregister.service.dto.OrderDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class OrderDTOMapperTest {

		@InjectMocks
		private OrderDTOMapper orderDTOMapper;


		@Test
		void map() {
				OrderDTO orderDTOInput = new OrderDTO(LocalDateTime.now());
				User userInput = new User(1L, "testName","testLastName", "CASHIER", "testUserName","test", new ArrayList<>(), new ArrayList<>());

				Order result = orderDTOMapper.map(orderDTOInput, userInput);
				assertEquals(orderDTOInput.getOpenDate(), result.getOpenDate());
				assertEquals(userInput.getFirstName(), result.getUser().getFirstName());
		}
}