package com.jk.cashregister.service.mapper;

import com.jk.cashregister.domain.Report;
import com.jk.cashregister.service.dto.ReportDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ReportDTOMapper {
		public Report map(ReportDTO reportDTO) {
				Report report = new Report();
				report.setToDate(LocalDateTime.now());
				report.setReportType(reportDTO.getReportType().toUpperCase());
				return report;
		}
}
