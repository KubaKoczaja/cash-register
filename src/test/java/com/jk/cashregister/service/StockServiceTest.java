package com.jk.cashregister.service;

import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.domain.dto.SearchDTO;
import com.jk.cashregister.domain.dto.StockDTO;
import com.jk.cashregister.repository.StockRepository;
import com.jk.cashregister.service.exception.NoSuchItemException;
import com.jk.cashregister.service.exception.NotEnoughQuantityException;
import com.jk.cashregister.service.validator.StockQuantityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

		@InjectMocks
		private StockService stockService;
		@Mock
		private StockRepository stockRepository;
		@Mock
		private StockQuantityValidator stockQuantityValidator;

		private Stock stockTest;

		private StockDTO stockDTOTest;
		private long	stockId;
		private int quantity;
		private ArgumentCaptor<Stock> stockArgumentCaptor;
		private Stock stockExpected;
		private List<Stock> list;


		@BeforeEach
		public void setUp() {
				stockTest = new Stock(1L, "aaa","test",20, 100,new ArrayList<>());
				stockDTOTest = new StockDTO("bbb","anotherTest", 5, 200);
				stockId = 1L;
				quantity = 10;
				stockArgumentCaptor = ArgumentCaptor.forClass(Stock.class);
				list = List.of(stockTest,stockTest);

		}

		@Test
		void shouldThrowExceptionWhenNoSuchId() {
				when(stockRepository.findById(stockId)).thenReturn(Optional.empty());
				assertThrows(NoSuchItemException.class, () -> stockService.getStockById(1L));
		}

		@Test
		void shouldReturnStockEntityById() {
				when(stockRepository.findById(anyLong())).thenReturn(Optional.of(stockTest));
				Stock stockResult = stockService.getStockById(stockId);

				assertEquals(stockResult.getProductName(), stockTest.getProductName());
		}

		@Test
		void shouldCreateNewStockFromStockDTO() {
				stockService.createStock(stockDTOTest);
				verify(stockRepository).save(stockArgumentCaptor.capture());
				stockExpected = stockArgumentCaptor.getValue();

				assertEquals(stockDTOTest.getProductName(), stockExpected.getProductName());
				assertEquals(stockDTOTest.getProductCode(), stockExpected.getProductCode());
				assertEquals(stockDTOTest.getPrice(), stockExpected.getPrice());
		}

		@Test
		void shouldUpdateStockWithGivenStockDTOAndId() {
				stockService.updateStock(stockDTOTest, stockId);
				verify(stockRepository).save(stockArgumentCaptor.capture());
				stockExpected = stockArgumentCaptor.getValue();

				assertEquals(stockDTOTest.getProductName(), stockExpected.getProductName());
				assertEquals(stockDTOTest.getProductCode(), stockExpected.getProductCode());
				assertEquals(stockDTOTest.getPrice(), stockExpected.getPrice());
		}

		@Test
		void shouldUpdateQuantityOfStockWithGivenId() {

				Integer expectedQuantity = 10;

				when(stockQuantityValidator.validateOrderedQuantity(anyInt(), anyInt())).thenReturn(true);
				when(stockRepository.findById(anyLong())).thenReturn(Optional.of(stockTest));
				stockService.updateQuantity(stockId,quantity);

				verify(stockRepository).save(stockArgumentCaptor.capture());
				stockExpected = stockArgumentCaptor.getValue();

				assertEquals(expectedQuantity, stockExpected.getQuantity());
		}

		@Test
		void shouldThrowExceptionExceptionWhenValidatedFalse() {

				when(stockRepository.findById(anyLong())).thenReturn(Optional.of(stockTest));
				when(stockQuantityValidator.validateOrderedQuantity(anyInt(), anyInt())).thenThrow(NotEnoughQuantityException.class);

				assertThrows(NotEnoughQuantityException.class, () -> stockService.updateQuantity(stockId,quantity)) ;
		}
		@Test
		void shouldReturnListWithSearchResultsWhenSearchingByCode() {
				SearchDTO searchDTO = new SearchDTO("aaa","");

				when(stockRepository.findByProductCodePattern(anyString())).thenReturn(list);
				List<Stock> result = stockService.getAllUsingSearch(searchDTO);
				verify(stockRepository, times(0)).findByProductNamePattern(anyString());
				assertEquals(list.size(), result.size());
		}
		@Test
		void shouldReturnListWithSearchResultsWhenSearchingByName() {
				SearchDTO searchDTO = new SearchDTO("","aaa");
				when(stockRepository.findByProductNamePattern(anyString())).thenReturn(list);
				List<Stock> result = stockService.getAllUsingSearch(searchDTO);
				verify(stockRepository, times(0)).findByProductCodePattern(anyString());
				assertEquals(list.size(), result.size());
		}
}
