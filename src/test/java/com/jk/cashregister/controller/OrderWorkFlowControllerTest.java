package com.jk.cashregister.controller;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.OrderItem;
import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.service.dto.OrderDTO;
import com.jk.cashregister.service.dto.SearchDTO;
import com.jk.cashregister.repository.OrderItemRepository;
import com.jk.cashregister.service.CashRegisterUserDetailsService;
import com.jk.cashregister.service.OrderService;
import com.jk.cashregister.service.OrderWorkflowService;
import com.jk.cashregister.service.StockService;
import com.jk.cashregister.util.Paging;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = OrderWorkFlowController.class)
@WithMockUser(username = "admin", roles = {"SENIOR_CASHIER","CASHIER"})
class OrderWorkFlowControllerTest {
		@Autowired
		private MockMvc mockMvc;
		@MockBean
		private CashRegisterUserDetailsService cashRegisterUserDetailsService;
		@MockBean
		private OrderWorkflowService orderWorkflowService;
		@MockBean
		private StockService stockService;
		@MockBean
		private OrderService orderService;
		@MockBean
		private OrderItemRepository orderItemRepository;
		@MockBean
		private Paging<OrderItem> pagingOrderItem;
		@MockBean
		private Paging<Stock> pagingStock;
		@MockBean
		private List<Stock> stockList;
		@MockBean
		private Page<Stock> stockPage;

		@BeforeEach
		void setUp() {
		}
		@Test
		void shouldOpenNewOrder() throws Exception {
				Order order = new Order();
				order.setOpenDate(LocalDateTime.now());
				when(orderWorkflowService.openNewOrder(any(OrderDTO.class))).thenReturn(order);
				mockMvc.perform(post("/order/openorder"))
								.andExpect(view().name("redirect:/order/" + order.getId() + "/editorder"))
								.andExpect(status().is3xxRedirection());
		}
		@Test
		void shouldDeleteNewOrder() throws Exception {
				mockMvc.perform(post("/order/{id}/deleteneworder", 1L))
								.andExpect(view().name("redirect:/order"))
								.andExpect(status().is3xxRedirection());
		}
		@Test
		void shouldCloseOrderWithGivenId() throws Exception {
				mockMvc.perform(get("/order/{id}/closeorder", 1L))
								.andExpect(view().name("redirect:/order"))
								.andExpect(status().is3xxRedirection());
		}

		@Test
		void shouldRedirectToSearchPageWhenSearchingByCode() throws Exception {
				mockMvc.perform(post("/order/{id}/editorder/search", 1L)
								.param("code", "test")
								.param("name", ""))
								.andExpect(view().name("redirect:/order/{id}/editorder/search"))
								.andExpect(status().is3xxRedirection());
		}
		@Test
		void shouldReturnSearchPageWithStockFilteredUsingSearchDTO() throws Exception {
				Order order = new Order();
				order.setId(1L);
				when(stockService.getAllUsingSearch(any(SearchDTO.class))).thenReturn(stockList);
				when(orderService.getOrderById(anyLong())).thenReturn(order);
				when(pagingStock.getPage(anyInt(), anyList(), anyInt())).thenReturn(stockPage);
				mockMvc.perform(get("/order/{id}/editorder/search", 1L))
								.andExpect(view().name("/order/{id}/editorder/search"))
								.andExpect(status().isOk());
		}
}