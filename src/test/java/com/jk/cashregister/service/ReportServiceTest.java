package com.jk.cashregister.service;

import com.jk.cashregister.domain.*;
import com.jk.cashregister.domain.dto.ReportDTO;
import com.jk.cashregister.repository.OrderRepository;
import com.jk.cashregister.repository.ReportRepository;
import com.jk.cashregister.service.exception.NoSuchItemException;
import com.jk.cashregister.service.mapper.ReportDTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {
		@InjectMocks
		private XReportGenerator xReportGenerator;
		@InjectMocks
		private ZReportGenerator zReportGenerator;
		@InjectMocks
		private ReportService reportService;
		@Mock
		private OrderRepository orderRepository;
		@Mock
		private ReportRepository reportRepository;
		@Mock
		private ReportDTOMapper reportDTOMapper;
		private List<Report> reportList;
		private List<Order> orderList;

		@BeforeEach
		void setUp() {
				reportList = new ArrayList<>();
				Report report1 = new Report(1L, LocalDateTime.of(2022,7,14, 20, 0, 0), LocalDateTime.of(2022,7,14, 21, 0, 0),"a","Z", new User());
				Report report2 = new Report(1L, LocalDateTime.of(2022,7,15, 14, 0, 0), LocalDateTime.of(2022,7,15, 16, 0, 0),"a","Z", new User());
				reportList.add(report1);
				reportList.add(report2);

				orderList = new ArrayList<>();
				Stock stock = new Stock(1L, "pc", "pn", 5, 1, new ArrayList<>());
				OrderItem orderItem = new OrderItem(1L, stock, 2, new Order());
				Order order1 = new Order(1L, LocalDateTime.now(), LocalDateTime.now(), new User(), List.of(orderItem, orderItem));
				orderItem.setOrder(order1);
				stock.setOrderItemList(List.of(orderItem));
				orderList.add(order1);
		}

		@Test
		void shouldProvideContentForGivenListOfOrders() {
				when(orderRepository.findAllByCloseDateGreaterThanAndCloseDateLessThanEqual(any(LocalDateTime.class), any(LocalDateTime.class)))
								.thenReturn(orderList);
				String expected = "Number of orders: 1\nItems stats: \npn : 4\n";
				String result = xReportGenerator.provideContent(LocalDateTime.now(), LocalDateTime.now());
				assertEquals(expected, result);
		}
		@Test
		void shouldProvideFromDateForXReportFromGivenListOfReports() {
				when(reportRepository.findAllByReportType(anyString())).thenReturn(reportList);
				LocalDateTime expected = LocalDateTime.of(2022,7,15, 16, 0, 0);
				LocalDateTime result = xReportGenerator.provideFromDate();
				assertEquals(expected, result);
		}
		@Test
		void shouldProvideFromDateForXReportWhenThereIsNoZReport() {
				when(reportRepository.findAllByReportType(anyString())).thenReturn(new ArrayList<>());
				LocalDateTime expected = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(1);
				LocalDateTime result = xReportGenerator.provideFromDate();
				assertEquals(expected, result);
		}
		@Test
		void shouldProvideFromDateForZReport() {
				LocalDateTime expected = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(1);
				LocalDateTime result = zReportGenerator.provideFromDate();
				assertEquals(expected, result);
		}
		@Test
		void shouldGenerateXReport() {
				Report reportInput = new Report(1L, LocalDateTime.now(), LocalDateTime.now(),"a","X", new User());
				ReportDTO reportDTOInput = new ReportDTO("X");
				when(reportDTOMapper.map(any(ReportDTO.class))).thenReturn(reportInput);
				when(reportRepository.findAllByReportType(anyString())).thenReturn(reportList);
				when(orderRepository.findAllByCloseDateGreaterThanAndCloseDateLessThanEqual(any(LocalDateTime.class), any(LocalDateTime.class)))
								.thenReturn(orderList);
				Report result = xReportGenerator.generateReport(reportDTOInput);
				assertEquals(reportDTOInput.getReportType(), result.getReportType());
				assertNotNull(result.getFromDate());
				assertNotNull(result.getContent());
		}
		@Test
		void shouldGenerateZReport() {
				Report reportInput = new Report(1L, LocalDateTime.now(), LocalDateTime.now(),"a","Z", new User());
				ReportDTO reportDTOInput = new ReportDTO("Z");
				when(reportDTOMapper.map(any(ReportDTO.class))).thenReturn(reportInput);
				when(orderRepository.findAllByCloseDateGreaterThanAndCloseDateLessThanEqual(any(LocalDateTime.class), any(LocalDateTime.class)))
								.thenReturn(orderList);
				Report result = zReportGenerator.generateReport(reportDTOInput);
				assertEquals(reportDTOInput.getReportType(), result.getReportType());
				assertNotNull(result.getFromDate());
				assertNotNull(result.getContent());
		}
		@Test
		void shouldThrowExceptionWhenIdDoesNotExists() {
				when(reportRepository.findById(anyLong())).thenThrow(NoSuchItemException.class);
				assertThrows(NoSuchItemException.class, () -> reportService.getReportById(1L));
		}
		@Test
		void shouldGetReportByGivenId() {
				Report reportInput = new Report(1L, LocalDateTime.now(), LocalDateTime.now(),"a","Z", new User());
				when(reportRepository.findById(anyLong())).thenReturn(Optional.of(reportInput));
				Report result = reportService.getReportById(1L);
				assertEquals(reportInput.getReportType(),result.getReportType());
		}
}