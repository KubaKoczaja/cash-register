package com.jk.cashregister.service;

import com.jk.cashregister.domain.Report;
import com.jk.cashregister.repository.ReportRepository;
import com.jk.cashregister.service.dto.ReportDTO;
import com.jk.cashregister.service.exception.NoSuchItemException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {
		private final ReportFactory reportFactory;
		private final ReportRepository reportRepository;

		@Transactional
		public void createReport(ReportDTO reportDTO) {

				log.info("creating report");
				ReportGenerator reportService = reportFactory.createService(reportDTO);
				Report report = reportService.generateReport(reportDTO);
				reportRepository.save(report);

				log.info("report created");
		}

		public Page<Report> getAllReports(int page, int size) {
				return reportRepository.findAll(PageRequest.of(page - 1, size));
		}

		public Report getReportById(Long id) {
				return reportRepository.findById(id).orElseThrow(() -> new NoSuchItemException("Report with such id doesn't exist"));
		}
}
