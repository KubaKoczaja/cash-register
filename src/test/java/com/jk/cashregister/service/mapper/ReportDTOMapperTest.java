package com.jk.cashregister.service.mapper;

import com.jk.cashregister.domain.Report;
import com.jk.cashregister.domain.User;
import com.jk.cashregister.service.dto.ReportDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ReportDTOMapperTest {
		@InjectMocks
		private ReportDTOMapper reportDTOMapper;

		@Test
		void shouldMapReportDTOToReport() {
				ReportDTO reportDTOInput = new ReportDTO("X");
				User userInput = new User(1L, "testName","testLastName", "CASHIER", "testUserName","test", new ArrayList<>(), new ArrayList<>());
				Report result = reportDTOMapper.map(reportDTOInput, userInput);
				assertEquals(reportDTOInput.getReportType(), result.getReportType());
				assertEquals(userInput.getFirstName(), result.getUser().getFirstName());
				assertNotNull(result.getToDate());
		}
}