package com.jk.cashregister.controller;

import com.jk.cashregister.domain.OrderItem;
import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.service.dto.StockDTO;
import com.jk.cashregister.repository.OrderItemRepository;
import com.jk.cashregister.service.CashRegisterUserDetailsService;
import com.jk.cashregister.service.StockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = StockController.class)
@WithMockUser(username="admin",roles={"COMMODITY_EXPERT"})
class StockControllerTest {
		@Autowired
		private MockMvc mockMvc;
		@MockBean
		private StockService stockService;
		@MockBean
		private OrderItemRepository orderItemRepository;
		@MockBean
		private CashRegisterUserDetailsService cashRegisterUserDetailsService;
		@MockBean
		private Page<Stock> pageList;
		@MockBean
		private Stock stock;
		@MockBean
		private StockDTO stockDTO;

		@Test
		void shouldReturnAllStockViewWithOkStatus() throws Exception {
				when(stockService.getAllStock(anyInt(),anyInt())).thenReturn(pageList);
				mockMvc.perform(get("/stock"))
								.andExpect(status().isOk());
		}

		@Test
		void shouldReturnViewStockByIdWithOkStatus() throws Exception {
				when(stockService.getStockById(anyLong())).thenReturn(stock);
				mockMvc.perform(get("/stock/{id}/details", anyLong()))
								.andExpect(status().isOk());
		}

		@Test
		void shouldAddNewValidStockWithOkStatus() throws Exception {
				Stock stockInput = new Stock(1L, "aaa","test",20, 100,new ArrayList<>());
				when(stockService.createStock(any(StockDTO.class))).thenReturn(stockInput);
				mockMvc.perform(post("/stock/addstock"))
								.andExpect(status().isOk());
		}

		@Test
		void shouldCatchBindExceptionAndReturnInputErrorPageWhileAddingInvalidStock() throws Exception {
				mockMvc.perform(post("/stock/addstock")
								.param("productCode", "")
								.param("productName", "")
								.param("quantity", "-1")
								.param("price","0"))
								.andExpect(view().name("/inputerror"))
								.andExpect(model().attributeExists("allErrors"))
								.andExpect(status().isOk());
		}

		@Test
		void shouldDeleteStockWithGivenId() throws Exception {
				when(orderItemRepository.findAllByStockId(anyLong())).thenReturn(new ArrayList<>());
				mockMvc.perform(post("/stock/{id}/delete", 1L))
								.andExpect(view().name("redirect:/stock"))
								.andExpect(status().is3xxRedirection());
		}
		@Test
		void shouldThrowExceptionWhenStockSetToBeDeleteIsInOrder() throws Exception {
				when(orderItemRepository.findAllByStockId(anyLong())).thenReturn(List.of(new OrderItem()));
				mockMvc.perform(post("/stock/{id}/delete", 1L))
								.andExpect(view().name("/customerror"))
								.andExpect(status().isOk());
		}
		@Test
		void shouldReturnUpdateStockView() throws Exception {
				when(stockService.getStockDTOFromId(anyLong())).thenReturn(stockDTO);
				mockMvc.perform(get("/stock/{id}/update", 1L))
								.andExpect(view().name("/stock/{id}/update"))
								.andExpect(model().attributeExists("stockDTOToUpdate"))
								.andExpect(model().attributeExists("stockId"))
								.andExpect(status().isOk());
		}
		@Test
		void shouldUpdateStockWithGivenIdUsingValidStockDTO() throws Exception {
				mockMvc.perform(post("/stock/{id}/update", 1L)
								.param("productCode", "a")
								.param("productName", "a")
								.param("quantity", "1")
								.param("price","10"))
								.andExpect(view().name("redirect:/stock"))
								.andExpect(status().is3xxRedirection());
		}
		@Test
		void shouldReturnInputErrorPageWhenUpdatingStockWithGivenIdWithInvalidStockDTO() throws Exception {
				mockMvc.perform(post("/stock/{id}/update", 1L)
								.param("productCode", "")
								.param("productName", "")
								.param("quantity", "-1")
								.param("price","0"))
								.andExpect(view().name("/inputerror"))
								.andExpect(model().attributeExists("allErrors"))
								.andExpect(status().isOk());
		}
}