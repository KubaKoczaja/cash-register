package com.jk.cashregister.service.mapper;

import com.jk.cashregister.domain.Report;
import com.jk.cashregister.domain.dto.ReportDTO;
import org.springframework.stereotype.Component;

@Component
public class ReportDTOMapper {
		public Report map(ReportDTO reportDTO) {
				Report report = new Report();
				report.setToDate(reportDTO.getToDate());
				report.setReportType(reportDTO.getReportType());
				report.setUser(reportDTO.getUser());
				return report;
		}
}
