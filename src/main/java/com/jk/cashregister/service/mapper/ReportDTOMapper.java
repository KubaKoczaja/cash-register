package com.jk.cashregister.service.mapper;

import com.jk.cashregister.domain.Report;
import com.jk.cashregister.domain.dto.ReportDTO;
import com.jk.cashregister.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ReportDTOMapper {
		private final UserService userService;

		public Report map(ReportDTO reportDTO) {
				Report report = new Report();
				report.setToDate(LocalDateTime.now());
				report.setReportType(reportDTO.getReportType().toUpperCase());
				report.setUser(userService.getAuthenticatedUser());
				return report;
		}
}
