package com.jk.cashregister.service.mapper;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.dto.OrderDTO;
import com.jk.cashregister.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderDTOMapper {
		private final UserService userService;
		//TODO get rid of session and obtain user using Spring Security
		public Order map(OrderDTO orderDTO) {
				Order order = new Order();
				order.setOpenDate(orderDTO.getOpenDate());
				order.setUser(userService.getLoggedUser());
				return order;
		}
}
