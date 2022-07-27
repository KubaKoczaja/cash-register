package com.jk.cashregister.service;

import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.domain.dto.StockCreateRequest;
import com.jk.cashregister.repository.StockRepository;
import com.jk.cashregister.service.exception.NoSuchStockItemException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

		@Mock
		private StockRepository stockRepository;

		@InjectMocks
		private StockService stockService;

		@Test
		void shouldThrowExceptionWhenNoSuchId() {
				Mockito.when(stockRepository.findById(1L)).thenReturn(Optional.empty());
				assertThrows(NoSuchStockItemException.class, () -> stockService.getById(1L));
		}

		@Test
		void shouldReturnStockEntityById() {
				Mockito.when(stockRepository.findById(2L))
								.thenReturn(Optional.of(new Stock(2L,"test", "test", 2, 1000, new ArrayList<>())));
				assertEquals("test", stockService.getById(2L).getProductName());
		}

		@Disabled
		@Test
		void getAllStock() {
		}

		@Disabled
		@Test
		void shouldCreateStock() {
				StockCreateRequest stockCreateRequest = new StockCreateRequest("test", "test", 2, 100);
				Stock expectedStock = stockService.createStock(stockCreateRequest);
				Mockito.when(stockRepository.findById(2L))
								.thenReturn(Optional.of(new Stock(2L,"test", "test", 2, 100, new ArrayList<>())));
				assertEquals(expectedStock.getProductName(), stockService.getById(2L).getProductName());
		}
}
