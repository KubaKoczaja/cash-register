package com.jk.cashregister.service;

import com.jk.cashregister.domain.OrderItem;
import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.service.dto.OrderItemDTO;
import com.jk.cashregister.repository.OrderItemRepository;
import com.jk.cashregister.repository.StockRepository;
import com.jk.cashregister.service.exception.NoSuchItemException;
import com.jk.cashregister.service.mapper.OrderItemDTOMapper;
import com.jk.cashregister.service.validator.StockQuantityValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderItemService {

		private final OrderItemRepository orderItemRepository;
		private final OrderItemDTOMapper orderItemDTOMapper;
		private final StockRepository stockRepository;
		private final StockQuantityValidator stockQuantityValidator;
		public OrderItem getOrderItemById(Long id) {
				return orderItemRepository.findById(id).orElseThrow(() -> new NoSuchItemException("Order item with such element doesn't exist"));
		}
		public OrderItemDTO getOrderItemDTOById(Long id) {
				OrderItem orderItem = getOrderItemById(id);
				return orderItemDTOMapper.mapToOrderItemDTO(orderItem);
		}
		@Transactional
		public void updateOrderItem(Long id, OrderItemDTO orderItemDTO) {
				log.info("Updating order with id: " + id);
				OrderItem orderItem = getOrderItemById(id);
				Stock stock = orderItem.getStock();
				log.info("Checking ordered quantity");
				int previousQuantityOrdered = stock.getQuantity() + orderItem.getQuantityOrdered();
				int newQuantityOrdered = orderItemDTO.getQuantityOrdered();
				stockQuantityValidator.validateOrderedQuantity(previousQuantityOrdered, newQuantityOrdered);

				int newStockQuantity = stock.getQuantity() + (orderItem.getQuantityOrdered() - orderItemDTO.getQuantityOrdered());
				stock.setQuantity(newStockQuantity);
				stockRepository.save(stock);
				orderItem.setQuantityOrdered(orderItemDTO.getQuantityOrdered());
				orderItemRepository.save(orderItem);
				log.info("OrderItem updated");
		}

		@Transactional
		public void deleteOrderItemFromOrder(Long id) {
				log.info("Deleting OrderItem from order");
				OrderItem orderItem = getOrderItemById(id);
				Stock stock = orderItem.getStock();
				stock.setQuantity(stock.getQuantity() + orderItem.getQuantityOrdered());
				log.info("Stock quantity in warehouse updated");
				stockRepository.save(stock);
				orderItemRepository.deleteById(id);
				log.info("OrderItem deleted");
		}
}
