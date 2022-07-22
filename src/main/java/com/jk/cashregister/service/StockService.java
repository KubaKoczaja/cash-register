package com.jk.cashregister.service;

import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.domain.dto.StockCreateRequest;
import com.jk.cashregister.repository.StockRepository;
import com.jk.cashregister.service.exception.NoSuchStockItemException;
import com.jk.cashregister.service.mapper.StockCreateRequestMapper;
import com.jk.cashregister.service.validator.StockCreateRequestValidator;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class StockService {
		private final StockRepository stockRepository;
		private final StockCreateRequestValidator stockCreateRequestValidator;
		private Pageable firstPageWithTenElements = PageRequest.of(0, 10);
		private final StockCreateRequestMapper stockCreateRequestMapper;

		@Autowired
		public StockService(StockRepository stockRepository, StockCreateRequestValidator stockCreateRequestValidator,
												StockCreateRequestMapper stockCreateRequestMapper) {
				this.stockRepository = stockRepository;
				this.stockCreateRequestValidator = stockCreateRequestValidator;
				this.stockCreateRequestMapper = stockCreateRequestMapper;
		}
// getting dto object instead database entity?
		public Stock getById(Long id) {
				return stockRepository.findById(id).orElseThrow(NoSuchStockItemException::new);
		}

		public List<Stock> getAllStock() {
				Page<Stock> all = stockRepository.findAll(firstPageWithTenElements);
				return all.getContent();
		}
//using dto object instead database entity
		public Stock createStock(StockCreateRequest request) {
				stockCreateRequestValidator.validate(request);
				Stock stock = stockCreateRequestMapper.mapToStock(request);
				stockRepository.save(stock);
				return stock;
		}
}

