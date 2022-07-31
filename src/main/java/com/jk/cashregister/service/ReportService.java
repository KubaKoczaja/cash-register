package com.jk.cashregister.service;

import com.jk.cashregister.domain.Report;
import com.jk.cashregister.domain.dto.ReportDTO;
import com.jk.cashregister.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {
		private final ReportFactory reportFactory;
		private final ReportRepository reportRepository;

		public Report saveReport(ReportDTO reportDTO) {
				ReportGenerate reportService = reportFactory.createService(reportDTO);
				Report report = reportService.generateReport(reportDTO);
				return reportRepository.save(report);
		}
}
