package com.jk.cashregister.service;

import com.jk.cashregister.domain.OrderItem;
import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.domain.dto.OrderItemDTO;
import com.jk.cashregister.repository.OrderItemRepository;
import com.jk.cashregister.repository.StockRepository;
import com.jk.cashregister.service.exception.NoSuchOrderItemException;
import com.jk.cashregister.service.mapper.OrderItemDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderItemService {

		private final OrderItemRepository orderItemRepository;
		private final OrderItemDTOMapper orderItemDTOMapper;
		private final StockRepository stockRepository;
		public OrderItem getOrderItemById(Long id) {
				return orderItemRepository.findById(id).orElseThrow(NoSuchOrderItemException::new);
		}
		public OrderItemDTO getOrderItemDTOById(Long id) {
				OrderItem orderItem = getOrderItemById(id);
				return orderItemDTOMapper.mapToOrderItemDTO(orderItem);
		}
		@Transactional
		public void updateOrderItem(Long id, OrderItemDTO orderItemDTO) {
				OrderItem orderItem = getOrderItemById(id);
				Stock stock = orderItem.getStock();
				int newStockQuantity = stock.getQuantity() + (orderItem.getQuantityOrdered() - orderItemDTO.getQuantityOrdered());
				stock.setQuantity(newStockQuantity);
				stockRepository.save(stock);
				orderItem.setQuantityOrdered(orderItemDTO.getQuantityOrdered());
				orderItemRepository.save(orderItem);
		}

		@Transactional
		public void deleteOrderItemFromOrder(Long id) {
				OrderItem orderItem = getOrderItemById(id);
				Stock stock = orderItem.getStock();
				stock.setQuantity(stock.getQuantity() + orderItem.getQuantityOrdered());
				stockRepository.save(stock);
				orderItemRepository.deleteById(id);
		}
}
