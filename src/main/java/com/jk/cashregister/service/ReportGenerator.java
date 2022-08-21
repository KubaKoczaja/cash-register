package com.jk.cashregister.service;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.Report;
import com.jk.cashregister.domain.User;
import com.jk.cashregister.repository.OrderRepository;
import com.jk.cashregister.service.dto.ReportDTO;
import com.jk.cashregister.service.mapper.ReportDTOMapper;
import com.jk.cashregister.util.LocalizedMessageProvider;
import liquibase.repackaged.org.apache.commons.lang3.tuple.ImmutablePair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public abstract class ReportGenerator {

		private final OrderRepository orderRepository;
		private final LocalizedMessageProvider provider;
		private final UserService userService;
		private final ReportDTOMapper reportDTOMapper;


		public Report generateReport(ReportDTO reportDTO) {
				User authUser = userService.getAuthenticatedUser();
				Report report = reportDTOMapper.map(reportDTO, authUser);
				LocalDateTime fromDate = provideFromDate();
				report.setFromDate(fromDate);
				report.setContent(provideContent(report.getFromDate(), report.getToDate()));
				log.info("Report with type " + report.getReportType());
				return report;
		}
		public abstract LocalDateTime provideFromDate();
		public String provideContent(LocalDateTime fromDate, LocalDateTime toDate) {
				List<Order> ordersPlacedInGivenTime = orderRepository.findAllByCloseDateGreaterThanAndCloseDateLessThanEqual(fromDate, toDate);
				Map<String, List<ImmutablePair<String, Integer>>> map = ordersPlacedInGivenTime
								.stream()
								.map(Order::getOrderItemList)
								.flatMap(List::stream)
								.map(o -> new ImmutablePair<>(o.getStock().getProductName(), o.getQuantityOrdered()))
								.collect(Collectors.groupingBy(p -> p.left));
				Map<String, Integer> result = new HashMap<>();
				for (Map.Entry<String, List<ImmutablePair<String, Integer>>> stringListEntry : map.entrySet()) {
						int sum = stringListEntry.getValue().stream().map(e -> e.right).reduce(0, Integer::sum);
						result.put(stringListEntry.getKey(), sum);
				}
				StringBuilder sb = new StringBuilder();
				result.forEach((key, value) -> sb.append(key).append(" : ").append(value).append("\n"));

				String numberOfOrdersString = provider.provideMessage("no_of_orders");
				String itemStatString = provider.provideMessage("item_stats");

				return 	numberOfOrdersString + ordersPlacedInGivenTime.size() + "\n" + itemStatString + "\n" + sb;
		}
}
