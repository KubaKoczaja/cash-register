package com.jk.cashregister.service.mapper;

import com.jk.cashregister.domain.Report;
import com.jk.cashregister.domain.User;
import com.jk.cashregister.domain.dto.ReportDTO;
import com.jk.cashregister.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportDTOMapperTest {
		@InjectMocks
		private ReportDTOMapper reportDTOMapper;
		@Mock
		private UserService userService;

		@Test
		void shouldMapReportDTOToReport() {
				ReportDTO reportDTOInput = new ReportDTO("X");
				when(userService.getAuthenticatedUser())
								.thenReturn(new User(1L, "a","a", "a","a", "a", new ArrayList<>(), new ArrayList<>()));
				Report result = reportDTOMapper.map(reportDTOInput);
				assertEquals(reportDTOInput.getReportType(), result.getReportType());
				assertNotNull(result.getUser());
				assertNotNull(result.getToDate());
		}
}