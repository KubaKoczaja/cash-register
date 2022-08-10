package com.jk.cashregister.service;

import com.jk.cashregister.domain.Report;
import com.jk.cashregister.repository.OrderRepository;
import com.jk.cashregister.repository.ReportRepository;
import com.jk.cashregister.service.mapper.ReportDTOMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class XReportGenerator extends ReportGenerator {

		private final ReportRepository reportRepository;

		public XReportGenerator(ReportDTOMapper reportDTOMapper, OrderRepository orderRepository, ReportRepository reportRepository) {
				super(reportDTOMapper, orderRepository);
				this.reportRepository = reportRepository;
		}
		@Override
		public LocalDateTime provideFromDate() {
				List<Report> reports = reportRepository.findAllByReportType("Z");
				reports.sort((o1, o2) -> o2.getToDate().compareTo(o1.getToDate()));
				if (reports.isEmpty()) {
						return LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(1);
				}
				return reports.get(0).getToDate();
		}
}
