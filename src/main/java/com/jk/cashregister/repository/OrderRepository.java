package com.jk.cashregister.repository;

import com.jk.cashregister.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
		List<Order> findAllByCloseDateGreaterThanAndCloseDateLessThanEqual (LocalDateTime closeDateFrom, LocalDateTime closeDateTo);
}
