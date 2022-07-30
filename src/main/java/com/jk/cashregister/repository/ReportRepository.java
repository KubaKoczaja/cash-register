package com.jk.cashregister.repository;

import com.jk.cashregister.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
		List<Report> findAllByReportType(String reportType);
}
