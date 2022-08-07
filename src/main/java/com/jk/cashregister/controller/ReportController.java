package com.jk.cashregister.controller;

import com.jk.cashregister.domain.Report;
import com.jk.cashregister.domain.dto.ReportDTO;
import com.jk.cashregister.service.ReportService;
import com.jk.cashregister.service.mapper.ReportDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
		private static final String REPORT_ROOT = "/report";
		public final ReportService reportService;
		private ReportDTOMapper reportDTOMapper;

		@GetMapping(name = "?page={page}")
		public String getAllReports(@RequestParam(defaultValue = "1") int page, Model model) {
				List<Report> allReports = reportService.getAllReports(page);
				model.addAttribute("allReports", allReports);
				model.addAttribute("currentPage", page);
				model.addAttribute("previousPage", page - 1);
				model.addAttribute("nextPage", page + 1);
				model.addAttribute("reportDTO", new ReportDTO());
				model.addAttribute("x","x");
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

				return "redirect:" + REPORT_ROOT;
		}
}
