package com.jk.cashregister.service;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.Report;
import com.jk.cashregister.domain.dto.ReportDTO;
import com.jk.cashregister.repository.OrderRepository;
import com.jk.cashregister.repository.ReportRepository;
import com.jk.cashregister.service.mapper.ReportDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportGenerateService {
		private final ReportDTOMapper reportDTOMapper;
		private final OrderRepository orderRepository;
		private final ReportRepository reportRepository;

		public Report generateReport(ReportDTO reportDTO) {
				Report report = reportDTOMapper.map(reportDTO);
				LocalDateTime fromDate = provideFromDate(report.getReportType());
				List<Order> ordersPlacedInGivenTime = orderRepository.findAllByOpenDateGreaterThanAndCloseDateLessThanEqual(fromDate, report.getToDate());
				report.setFromDate(fromDate);
				report.setNumberOfOrders(ordersPlacedInGivenTime.size());
				return reportRepository.save(report);
		}

		private LocalDateTime provideFromDate(String type) {
				List<Report> reports = reportRepository.findAllByReportType(type);
				reports.sort((o1, o2) -> o2.getToDate().compareTo(o1.getToDate()));
				if (reports.isEmpty()) {
						return LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(1);
				}
				return reports.get(0).getToDate();
		}
}
