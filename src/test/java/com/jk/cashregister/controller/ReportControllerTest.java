package com.jk.cashregister.controller;

import com.jk.cashregister.domain.Report;
import com.jk.cashregister.domain.User;
import com.jk.cashregister.service.CashRegisterUserDetailsService;
import com.jk.cashregister.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = ReportController.class)
@WithMockUser(username = "admin", roles = {"SENIOR_CASHIER"})
class ReportControllerTest {
		@Autowired
		private MockMvc mockMvc;
		@MockBean
		private CashRegisterUserDetailsService cashRegisterUserDetailsService;
		@MockBean
		private ReportService reportService;
		@MockBean
		private Page<Report> pageList;

		@Test
		void shouldReturnAllReportsViewPage() throws Exception {
				when(reportService.getAllReports(anyInt(),anyInt())).thenReturn(pageList);
				mockMvc.perform(get("/report"))
								.andExpect(view().name("/report"))
								.andExpect(status().isOk());
		}
		@Test
		void shouldReturnReportDetailsPageForReportWithGivenId() throws Exception {
				Report report = new Report(1L, LocalDateTime.now(), LocalDateTime.now(),"a","X", new User(1L,"test","test","SENIOR_CASHIER","test", "test", new ArrayList<>(), new ArrayList<>()));
				when(reportService.getReportById(anyLong())).thenReturn(report);
				mockMvc.perform(get("/report/{id}/details", 1L))
								.andExpect(view().name("/report/{id}/details"))
								.andExpect(status().isOk());
		}
		@Test
		void shouldGenerateReportUsingValidReportDTO() throws Exception {
				mockMvc.perform(post("/report/generatereport")
								.param("reportType", "X"))
								.andExpect(view().name("redirect:/report"))
								.andExpect(status().is3xxRedirection());
		}
		@Test
		void shouldReturnInputErrorPageForInvalidReportDTOInput() throws Exception {
				mockMvc.perform(post("/report/generatereport")
												.param("reportType", ""))
								.andExpect(view().name("/inputerror"))
								.andExpect(status().isOk());
		}
}