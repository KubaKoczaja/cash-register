package com.jk.cashregister.service;

import com.jk.cashregister.repository.OrderRepository;
import com.jk.cashregister.service.mapper.ReportDTOMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ZReportGenerator extends ReportGenerator {

		public ZReportGenerator(ReportDTOMapper reportDTOMapper, OrderRepository orderRepository) {
				super(reportDTOMapper, orderRepository);
		}

		// Z Report will always prepare content of report from midnight to now
		@Override
		public LocalDateTime provideFromDate() {
				return LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(1);
		}
}
