package com.jk.cashregister.repository;

import com.jk.cashregister.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
		@Query(value = "select * from stock s where UPPER(s.product_code) like %:code%", nativeQuery = true)
		List<Stock> findByProductCodePattern(@Param("code") String code);

		@Query(value = "select * from stock s where UPPER(s.product_name) like %:name%", nativeQuery = true)
		List<Stock> findByProductNamePattern(@Param("name") String name);
}
