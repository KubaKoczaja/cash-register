package com.jk.cashregister.service;

import com.jk.cashregister.repository.OrderRepository;
import com.jk.cashregister.repository.ReportRepository;
import com.jk.cashregister.service.dto.ReportDTO;
import com.jk.cashregister.service.mapper.ReportDTOMapper;
import com.jk.cashregister.util.LocalizedMessageProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.jk.cashregister.util.Strings.X;

@Component
@RequiredArgsConstructor
public class ReportFactory {
		private final OrderRepository orderRepository;
		private final ReportRepository reportRepository;
		private final LocalizedMessageProvider provider;
		private final UserService userService;
		private final ReportDTOMapper reportDTOMapper;
		public ReportGenerator createService(ReportDTO reportDTO) {
				if (reportDTO.getReportType().equalsIgnoreCase(X)) {
						return new XReportGenerator(orderRepository, provider, userService, reportDTOMapper, reportRepository);
				}
				return new ZReportGenerator(orderRepository, provider, userService, reportDTOMapper);
		}
}
