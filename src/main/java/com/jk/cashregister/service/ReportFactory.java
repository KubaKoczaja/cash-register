package com.jk.cashregister.service;

import com.jk.cashregister.domain.dto.ReportDTO;
import com.jk.cashregister.repository.OrderRepository;
import com.jk.cashregister.repository.ReportRepository;
import com.jk.cashregister.service.exception.InvalidReportTypeException;
import com.jk.cashregister.service.mapper.ReportDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportFactory {
		private final ReportDTOMapper reportDTOMapper;
		private final OrderRepository orderRepository;
		private final ReportRepository reportRepository;
		public ReportGenerate createService(ReportDTO reportDTO) {
				if (reportDTO.getReportType().equalsIgnoreCase("X")) {
						return new XReportGenerate(reportDTOMapper, orderRepository, reportRepository);
				} else if (reportDTO.getReportType().equalsIgnoreCase("Z")) {
						return new ZReportGenerate(reportDTOMapper, orderRepository, reportRepository);
				} else {
						throw new InvalidReportTypeException();
				}
		}
}
