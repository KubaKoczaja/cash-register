package com.jk.cashregister.service;

import com.jk.cashregister.domain.Report;
import com.jk.cashregister.domain.dto.ReportDTO;

import java.time.LocalDateTime;

public interface ReportGenerate {
		Report generateReport(ReportDTO reportDTO);
		LocalDateTime provideFromDate();
		String provideContent(LocalDateTime fromDate, LocalDateTime toDate);
}
