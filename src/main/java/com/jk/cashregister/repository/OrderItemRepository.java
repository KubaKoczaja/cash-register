package com.jk.cashregister.repository;

import com.jk.cashregister.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

		List<OrderItem> findAllByOrderId(Long id);
		List<OrderItem> findAllByStockId(Long id);
		@Modifying
		@Query(value = "DELETE FROM order_item WHERE order_id=:id", nativeQuery = true)
		void deleteAllByOrderId(@Param("id") Long id);
}
