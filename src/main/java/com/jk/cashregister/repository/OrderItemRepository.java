package com.jk.cashregister.repository;

import com.jk.cashregister.domain.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

		List<OrderItem> findAllByOrderId(Long id);
}
