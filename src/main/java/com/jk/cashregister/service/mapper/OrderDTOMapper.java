package com.jk.cashregister.service.mapper;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.User;
import com.jk.cashregister.service.dto.OrderDTO;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class OrderDTOMapper {
		public Order map(OrderDTO orderDTO, User user) {
				Order order = new Order();
				order.setOpenDate(orderDTO.getOpenDate());
				order.setUser(user);
				return order;
		}
}
