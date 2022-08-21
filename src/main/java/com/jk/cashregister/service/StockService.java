package com.jk.cashregister.service;

import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.service.dto.SearchDTO;
import com.jk.cashregister.service.dto.StockDTO;
import com.jk.cashregister.repository.StockRepository;
import com.jk.cashregister.service.exception.NoSuchItemException;
import com.jk.cashregister.service.mapper.StockDTOMapper;
import com.jk.cashregister.service.validator.StockQuantityValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
		private final StockRepository stockRepository;
		private final StockQuantityValidator stockQuantityValidator;

		public Stock getStockById(Long id) {
				return stockRepository.findById(id).orElseThrow(() -> new NoSuchItemException("Stock with such id doesn't exist"));
		}

		public StockDTO getStockDTOFromId(Long id){
				Stock stock = getStockById(id);
				return StockDTOMapper.INSTANCE.stockToStockDTO(stock);
		}

		public Page<Stock> getAllStock(int page, int size) {
				return stockRepository.findAll(PageRequest.of(page - 1, size));
		}

		@Transactional
		public Stock createStock(StockDTO request) {
				Stock stock = StockDTOMapper.INSTANCE.stockDTOToStock(request);
				return stockRepository.save(stock);
		}

		@Transactional
		public void updateStock(StockDTO request, Long stockToUpdateId) {
				Stock stock = StockDTOMapper.INSTANCE.stockDTOToStock(request);
				stock.setId(stockToUpdateId);
				stockRepository.save(stock);
		}

		@Transactional
		public void updateQuantity(long stockId, int newQuantity) {
				Stock byId = getStockById(stockId);
				stockQuantityValidator.validateOrderedQuantity(byId.getQuantity(), newQuantity);
				byId.setQuantity(byId.getQuantity() - newQuantity);
				stockRepository.save(byId);
		}

		public List<Stock> getAllUsingSearch(SearchDTO searchDTO) {
				List<Stock>	stockList;
				if (searchDTO.getCode().isBlank() && !searchDTO.getName().isBlank()) {
						stockList = stockRepository.findByProductNamePattern(searchDTO.getName().toUpperCase());
				} else if (!searchDTO.getCode().isBlank() && searchDTO.getName().isBlank()) {
						stockList = stockRepository.findByProductCodePattern(searchDTO.getCode().toUpperCase());
				} else {
						stockList = stockRepository.findAll();
				}
				return stockList;
		}
}

