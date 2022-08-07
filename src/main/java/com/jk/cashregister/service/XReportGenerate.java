package com.jk.cashregister.service;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.Report;
import com.jk.cashregister.domain.dto.ReportDTO;
import com.jk.cashregister.repository.OrderRepository;
import com.jk.cashregister.repository.ReportRepository;
import com.jk.cashregister.service.mapper.ReportDTOMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class XReportGenerate implements ReportGenerate {
		private final ReportDTOMapper reportDTOMapper;
		private final OrderRepository orderRepository;
		private final ReportRepository reportRepository;
		@Override
		public Report generateReport(ReportDTO reportDTO) {
				Report report = reportDTOMapper.map(reportDTO);
				System.out.println("-1--");
				LocalDateTime fromDate = provideFromDate();
				report.setFromDate(fromDate);
				report.setContent(provideContent(report.getFromDate(), report.getToDate()));
				return report;
		}

		@Override
		public LocalDateTime provideFromDate() {
				List<Report> reports = reportRepository.findAllByReportType("X");
				reports.sort((o1, o2) -> o2.getToDate().compareTo(o1.getToDate()));
				if (reports.isEmpty()) {
						return LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(1);
				}
				return reports.get(0).getToDate();
		}

		@Override
		public String provideContent(LocalDateTime fromDate, LocalDateTime toDate) {
				List<Order> ordersPlacedInGivenTime = orderRepository.findAllByOpenDateGreaterThanAndCloseDateLessThanEqual(fromDate, toDate);
				return "Number of orders: " + ordersPlacedInGivenTime.size();
		}
}
