package com.jk.cashregister.controller;

import com.jk.cashregister.service.CashRegisterUserDetailsService;
import com.jk.cashregister.service.OrderService;
import com.jk.cashregister.util.LocalizedMessageProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MainController.class)
class MainControllerTest {
		@Autowired
		private MockMvc mockMvc;
		@MockBean
		private UserDetailsService userDetailsService;
		@MockBean
		private OrderService orderService;
		@MockBean
		private CashRegisterUserDetailsService cashRegisterUserDetailsService;
		@MockBean
		private LocalizedMessageProvider provider;


		@Test
		void shouldReturnIndexPageWithStatusOk() throws Exception {
				mockMvc.perform(get("/index"))
								.andExpect(status().isOk());
		}

		@Test
		void shouldReturnLoginPageWithStatusOk() throws Exception {
				mockMvc.perform(get("/login"))
								.andExpect(status().isOk());
		}

		@Test
		void shouldReturnLogoutPageWithStatusOK() throws Exception {
				mockMvc.perform(get("/logout-success"))
								.andExpect(status().isOk());
		}
}