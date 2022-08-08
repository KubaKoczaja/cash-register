package com.jk.cashregister.controller;

import com.jk.cashregister.domain.Report;
import com.jk.cashregister.domain.dto.ReportDTO;
import com.jk.cashregister.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.jk.cashregister.util.Strings.*;
import static com.jk.cashregister.util.URLs.REDIRECT;
import static com.jk.cashregister.util.URLs.REPORT_ROOT;

@Controller
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
		public final ReportService reportService;

		@GetMapping
		public String getAllReports(@RequestParam(value = "page", defaultValue = "1") int page,
																@RequestParam(value = "size", defaultValue = "5") int size,
																Model model) {
				Page<Report> allReports = reportService.getAllReports(page,size);
				model.addAttribute("allReports", allReports.getContent());
				model.addAttribute(CURRENT_PAGE, page);
				model.addAttribute(PREVIOUS_PAGE, page - 1);
				model.addAttribute(NEXT_PAGE, page + 1);
				model.addAttribute("reportDTO", new ReportDTO());
				model.addAttribute(NUMBER_OF_PAGES, allReports.getTotalPages());
				return REPORT_ROOT;
		}

		@GetMapping("/{id}/details")
		public String getDetailsOnReport(@PathVariable Long id, Model model) {
				Report report = reportService.getReportById(id);
				model.addAttribute("reportDetails", report);
				return REPORT_ROOT + "/{id}/details";
		}

		@PostMapping("/generatereport")
		@PreAuthorize("hasRole('ROLE_SENIOR_CASHIER')")
		public String generateXReport(@Valid ReportDTO reportDTO) {
				reportService.saveReport(reportDTO);
				return REDIRECT + REPORT_ROOT;
		}
}
