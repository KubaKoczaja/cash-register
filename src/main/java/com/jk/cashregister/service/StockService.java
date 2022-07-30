package com.jk.cashregister.service;

import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.domain.dto.StockDTO;
import com.jk.cashregister.repository.StockRepository;
import com.jk.cashregister.service.exception.NoSuchStockItemException;
import com.jk.cashregister.service.exception.NotEnoughQuantityException;
import com.jk.cashregister.service.mapper.StockDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
		private final StockRepository stockRepository;

		private final StockDTOMapper stockDTOMapper;

// getting dto object instead database entity?
		public Stock getById(Long id) {
				return stockRepository.findById(id).orElseThrow(NoSuchStockItemException::new);
		}

		public List<Stock> getAllStock(int page) {
				Page<Stock> all = stockRepository.findAll(PageRequest.of(page - 1, 5));
				return all.getContent();
		}
//using dto object instead database entity
		public Stock createStock(StockDTO request) {
				Stock stock = stockDTOMapper.mapToStock(request);
				return stockRepository.save(stock);
		}

		public Stock updateStock(StockDTO request, Long stockToUpdateId) {
				Stock stock = stockDTOMapper.mapToStock(request);
				stock.setId(stockToUpdateId);
				return stockRepository.save(stock);
		}

		public void updateQuantity(long stockId, int newQuantity) {
				Stock byId = getById(stockId);
				if (byId.getQuantity() >= newQuantity) {
						byId.setQuantity(newQuantity);
				} else {
						throw new NotEnoughQuantityException();
				}
				stockRepository.save(byId);
		}

		public void deleteStockById(Long id) {
				stockRepository.deleteById(id);
		}
}

