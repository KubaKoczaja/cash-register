package com.jk.cashregister.service.mapper;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.User;
import com.jk.cashregister.domain.dto.OrderOpenRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class OrderOpenRequestMapper {
		//TODO get rid of session and obtain user using Spring Security
		private final HttpSession session;

		public OrderOpenRequestMapper(HttpSession session) {
				this.session = session;
		}

		public Order map(OrderOpenRequest orderOpenRequest) {
				Order order = new Order();
				order.setOpenDate(orderOpenRequest.getOpenDate());
				order.setUser((User)session.getAttribute("sessionUser"));
				return order;
		}
}
