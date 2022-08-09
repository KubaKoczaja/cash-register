package com.jk.cashregister.service;

import com.jk.cashregister.domain.Report;
import com.jk.cashregister.domain.dto.ReportDTO;
import com.jk.cashregister.repository.ReportRepository;
import com.jk.cashregister.service.exception.NoSuchItemException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

		public Page<Report> getAllReports(int page, int size) {
				return reportRepository.findAll(PageRequest.of(page - 1, size));
		}

		public Report getReportById(Long id) {
				return reportRepository.findById(id).orElseThrow(() -> new NoSuchItemException("Report with such id doesn't exist"));
		}
}
