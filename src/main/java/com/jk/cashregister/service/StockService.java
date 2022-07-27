package com.jk.cashregister.service;

import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.domain.dto.StockCreateRequest;
import com.jk.cashregister.repository.StockRepository;
import com.jk.cashregister.service.exception.NoSuchStockItemException;
import com.jk.cashregister.service.mapper.StockCreateRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
		private final StockRepository stockRepository;

		private Pageable firstPageWithTenElements = PageRequest.of(0, 10);
		private final StockCreateRequestMapper stockCreateRequestMapper;

//		@Autowired
//		public StockService(StockRepository stockRepository,
//												StockCreateRequestMapper stockCreateRequestMapper) {
//				this.stockRepository = stockRepository;
//				this.stockCreateRequestMapper = stockCreateRequestMapper;
//		}
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
				Stock stock = stockCreateRequestMapper.mapToStock(request);
				stockRepository.save(stock);
				return stock;
		}

		public void updateStock(StockCreateRequest request, Stock stockToUpdate) {
				Stock stock = stockCreateRequestMapper.mapToStock(request);
				stock.setId(stockToUpdate.getId());
				stockRepository.save(stock);
		}
}

