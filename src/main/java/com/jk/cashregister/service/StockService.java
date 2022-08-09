package com.jk.cashregister.service;

import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.domain.dto.StockDTO;
import com.jk.cashregister.repository.StockRepository;
import com.jk.cashregister.service.exception.NoSuchItemException;
import com.jk.cashregister.service.exception.NotEnoughQuantityException;
import com.jk.cashregister.service.mapper.StockDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {
		private final StockRepository stockRepository;

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
//using dto object instead database entity
		public Stock createStock(StockDTO request) {
				Stock stock = StockDTOMapper.INSTANCE.stockDTOToStock(request);
				return stockRepository.save(stock);
		}

		public void updateStock(StockDTO request, Long stockToUpdateId) {
				Stock stock = StockDTOMapper.INSTANCE.stockDTOToStock(request);
				stock.setId(stockToUpdateId);
				stockRepository.save(stock);
		}

		public void updateQuantity(long stockId, int newQuantity) {
				Stock byId = getStockById(stockId);
				if (byId.getQuantity() >= newQuantity) {
						byId.setQuantity(byId.getQuantity() - newQuantity);
				} else {
						throw new NotEnoughQuantityException("There is not enough stock left in warehouse");
				}
				stockRepository.save(byId);
		}

		public void deleteStockById(Long id) {
				stockRepository.deleteById(id);
		}
}

