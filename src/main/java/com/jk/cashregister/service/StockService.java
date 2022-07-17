package com.jk.cashregister.service;

import com.jk.cashregister.model.Stock;
import com.jk.cashregister.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {
		private final StockRepository stockRepository;

		public StockService(StockRepository stockRepository) {
				this.stockRepository = stockRepository;
		}

		Stock returnById(Long id) {
				return stockRepository.findById(id).orElse(null);
		}
}
