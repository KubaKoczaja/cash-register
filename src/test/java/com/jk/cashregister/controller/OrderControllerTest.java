package com.jk.cashregister.controller;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.OrderItem;
import com.jk.cashregister.repository.OrderItemRepository;
import com.jk.cashregister.service.CashRegisterUserDetailsService;
import com.jk.cashregister.service.OrderItemService;
import com.jk.cashregister.service.OrderService;
import com.jk.cashregister.util.Paging;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = OrderController.class)
@WithMockUser(username = "admin", roles = {"SENIOR_CASHIER","CASHIER"})
class OrderControllerTest {
		@Autowired
		private MockMvc mockMvc;
		@MockBean
		private CashRegisterUserDetailsService cashRegisterUserDetailsService;
		@MockBean
		private OrderService orderService;
		@MockBean
		private OrderItemRepository orderItemRepository;
		@MockBean
		private OrderItemService orderItemService;
		@MockBean
		private Page<Order> orderPage;
		@MockBean
		private Paging<OrderItem> orderItemPaging;

		@Test
		void shouldReturnAllOrdersView() throws Exception {
				when(orderService.getAllOrders(anyInt(), anyInt())).thenReturn(orderPage);
				mockMvc.perform(get("/order"))
								.andExpect(view().name("/order"))
								.andExpect(status().isOk());
		}

		@Test
		void shouldDeleteOrderWithItemsAndReturnAllOrdersView() throws Exception {
				mockMvc.perform(post("/order/{id}/delete", 1L))
								.andExpect(view().name("redirect:/order"))
								.andExpect(status().is3xxRedirection());
		}
		@Test
		void shouldDeleteItemFromOrderWithGivenIdAndReturnOrderDetailsView() throws Exception {
				mockMvc.perform(post("/order/{id}/details/{itemId}/deleteitem", 1L, 1L))
								.andExpect(view().name("redirect:/order/{id}/details"))
								.andExpect(status().is3xxRedirection());
		}
		@Test
		void shouldUpdateOrderItemWithGivenIdInOrderWithGivenIdAndReturnOrderDetailsView() throws Exception {
				mockMvc.perform(post("/order/{id}/details/{itemId}/updateitem", 1L, 1L)
								.param("stockId", "1")
								.param("quantityOrdered", "10"))
								.andExpect(status().is3xxRedirection());

		}
		@Test
		void shouldReturnInputErrorViewWhileUpdatingOrderItemWithInvalidOrderItemDTOInput() throws Exception {
				mockMvc.perform(post("/order/{id}/details/{itemId}/updateitem", 1L, 1L)
								.param("stockId", "1")
								.param("quantityOrdered", "-1"))
								.andExpect(view().name("/inputerror"))
								.andExpect(status().isOk());
		}
}