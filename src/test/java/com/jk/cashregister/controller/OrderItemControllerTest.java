package com.jk.cashregister.controller;

import com.jk.cashregister.service.CashRegisterUserDetailsService;
import com.jk.cashregister.service.OrderItemService;
import com.jk.cashregister.service.OrderWorkflowService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = OrderItemController.class)
@WithMockUser(username = "admin", roles = {"SENIOR_CASHIER","CASHIER"})
class OrderItemControllerTest {
		@Autowired
		private MockMvc mockMvc;
		@MockBean
		private CashRegisterUserDetailsService cashRegisterUserDetailsService;
		@MockBean
		private OrderWorkflowService orderWorkflowService;
		@MockBean
		private OrderItemService orderItemService;

		@Test
		void shouldAddItemToOrderAndReturnOrderEditView() throws Exception {
				mockMvc.perform(post("/order/{id}/editorder/additem", 1L)
								.param("stockId", "1")
								.param("quantityOrdered", "10"))
								.andExpect(view().name("redirect:/order/{id}/editorder"))
								.andExpect(status().is3xxRedirection());
		}
		@Test
		void shouldUpdateOrderItemInOrderWithGivenIdAndReturnEditOrderView() throws Exception {
				mockMvc.perform(post("/order/{id}/editorder/{itemId}/updateitem", 1L, 1L)
								.param("stockId", "1")
								.param("quantityOrdered", "10"))
								.andExpect(view().name("redirect:/order/{id}/editorder"))
								.andExpect(status().is3xxRedirection());
		}
		@Test
		void shouldReturnInputErrorViewWhileUpdatingOrderItemWithInvalidOrderItemDTO() throws Exception {
				mockMvc.perform(post("/order/{id}/editorder/{itemId}/updateitem", 1L, 1L)
								.param("stockId", "1")
								.param("quantityOrdered", "-1"))
								.andExpect(view().name("/inputerror"))
								.andExpect(status().isOk());
		}
		@Test
		void shouldDeleteOrderItemWithGivenIdFromOrderWithGivenIdAnrReturnEditOrderView() throws Exception {
				mockMvc.perform(post("/order/{id}/editorder/{itemId}/deleteitem", 1L, 1L))
								.andExpect(view().name("redirect:/order/{id}/editorder"))
								.andExpect(status().is3xxRedirection());
		}

}