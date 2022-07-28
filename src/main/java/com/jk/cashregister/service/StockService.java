package com.jk.cashregister.service;

import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.domain.dto.StockCreateRequest;
import com.jk.cashregister.repository.StockRepository;
import com.jk.cashregister.service.exception.NoSuchStockItemException;
import com.jk.cashregister.service.mapper.StockCreateRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
		private final StockRepository stockRepository;

		private final StockCreateRequestMapper stockCreateRequestMapper;

// getting dto object instead database entity?
		public Stock getById(Long id) {
				return stockRepository.findById(id).orElseThrow(NoSuchStockItemException::new);
		}

		public List<Stock> getAllStock(int page) {
				Page<Stock> all = stockRepository.findAll(PageRequest.of(page - 1, 1));
				return all.getContent();
		}
//using dto object instead database entity
		public Stock createStock(StockCreateRequest request) {
				Stock stock = stockCreateRequestMapper.mapToStock(request);
				return stockRepository.save(stock);
		}

		public Stock updateStock(StockCreateRequest request, Long stockToUpdateId) {
				Stock stock = stockCreateRequestMapper.mapToStock(request);
				stock.setId(stockToUpdateId);
				return stockRepository.save(stock);
		}

		public void deleteStockById(Long id) {
				stockRepository.deleteById(id);
		}
}

