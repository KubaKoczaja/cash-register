package com.jk.cashregister.service;

import com.jk.cashregister.domain.OrderItem;
import com.jk.cashregister.domain.dto.OrderItemDTO;
import com.jk.cashregister.repository.OrderItemRepository;
import com.jk.cashregister.service.exception.NoSuchOrderItemException;
import com.jk.cashregister.service.mapper.OrderItemDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemService {

		private final OrderItemRepository orderItemRepository;
		private final OrderItemDTOMapper orderItemDTOMapper;
		public OrderItem getById(Long id) {
				return orderItemRepository.findById(id).orElseThrow(NoSuchOrderItemException::new);
		}

		public OrderItem updateOrderItem(OrderItemDTO orderItemDTO, Long id) {
				OrderItem orderItem = orderItemDTOMapper.map(orderItemDTO);
				orderItem.setId(id);
				return orderItemRepository.save(orderItem);
		}
}
