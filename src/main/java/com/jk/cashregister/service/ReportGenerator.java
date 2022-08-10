package com.jk.cashregister.service;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.Report;
import com.jk.cashregister.domain.dto.ReportDTO;
import com.jk.cashregister.repository.OrderRepository;
import com.jk.cashregister.service.mapper.ReportDTOMapper;
import liquibase.repackaged.org.apache.commons.lang3.tuple.ImmutablePair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public abstract class ReportGenerator {
		private final ReportDTOMapper reportDTOMapper;
		private final OrderRepository orderRepository;

		public Report generateReport(ReportDTO reportDTO) {
				Report report = reportDTOMapper.map(reportDTO);
				LocalDateTime fromDate = provideFromDate();
				report.setFromDate(fromDate);
				report.setContent(provideContent(report.getFromDate(), report.getToDate()));
				return report;
		}
		public abstract LocalDateTime provideFromDate();
		public String provideContent(LocalDateTime fromDate, LocalDateTime toDate) {
				List<Order> ordersPlacedInGivenTime = orderRepository
								.findAllByOpenDateGreaterThanAndCloseDateLessThanEqual(fromDate, toDate);
				Map<String, List<ImmutablePair<String,Integer>>> map = ordersPlacedInGivenTime
								.stream()
								.map(Order::getOrderItemList)
								.flatMap(List::stream)
								.map(o -> new ImmutablePair<>(o.getStock().getProductName(), o.getQuantityOrdered()))
								.collect(Collectors.groupingBy(p -> p.left));
				Map<String, Integer> result = new HashMap<>();
				for (Map.Entry<String, List<ImmutablePair<String, Integer>>> stringListEntry : map.entrySet()) {
						int sum = stringListEntry.getValue()
										.stream()
										.map(e -> e.right)
										.reduce(0, Integer::sum);
						result.put(stringListEntry.getKey(), sum);
				}
				StringBuilder sb = new StringBuilder();
				result.forEach((key, value) -> sb.append(key).append(" : ").append(value).append("\n"));
				return "Number of orders: "
								+ ordersPlacedInGivenTime.size()
								+ "\nItems stats: \n"
								+ sb;
		}
}
