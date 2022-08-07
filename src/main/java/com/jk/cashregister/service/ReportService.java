package com.jk.cashregister.service;

import com.jk.cashregister.domain.Report;
import com.jk.cashregister.domain.dto.ReportDTO;
import com.jk.cashregister.repository.ReportRepository;
import com.jk.cashregister.service.exception.NoSuchReportException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

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

		public List<Report> getAllReports(int page) {
				Page<Report> all = reportRepository.findAll(PageRequest.of(page - 1, 5));
				return all.getContent();
		}

		public Report getReportById(Long id) {
				return reportRepository.findById(id).orElseThrow(NoSuchReportException::new);
		}
}
