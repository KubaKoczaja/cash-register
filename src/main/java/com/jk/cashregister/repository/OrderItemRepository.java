package com.jk.cashregister.repository;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
		List<OrderItem> findAllByOrder(Order order);
}
