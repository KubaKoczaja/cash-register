package com.jk.cashregister.repository;

import com.jk.cashregister.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
		@Query(value = "select * from report r where r.report_type=:reportType", nativeQuery = true)
		List<Report> findAllByReportType(@Param(value = "reportType") String reportType);
}
