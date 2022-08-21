package com.jk.cashregister.service;

import com.jk.cashregister.domain.Report;
import com.jk.cashregister.repository.OrderRepository;
import com.jk.cashregister.repository.ReportRepository;
import com.jk.cashregister.service.mapper.ReportDTOMapper;
import com.jk.cashregister.util.LocalizedMessageProvider;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.jk.cashregister.util.Strings.Z;

@Service
public class XReportGenerator extends ReportGenerator {

		private final ReportRepository reportRepository;

		public XReportGenerator(OrderRepository orderRepository, LocalizedMessageProvider provider, UserService userService, ReportDTOMapper reportDTOMapper, ReportRepository reportRepository) {
				super(orderRepository, provider, userService, reportDTOMapper);
				this.reportRepository = reportRepository;
		}


		@Override
		public LocalDateTime provideFromDate() {
				List<Report> reports = reportRepository.findAllByReportType(Z);
				reports.sort((o1, o2) -> o2.getToDate().compareTo(o1.getToDate()));
				if (reports.isEmpty()) {
						return LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(1);
				}
				return reports.get(0).getToDate();
		}
}
