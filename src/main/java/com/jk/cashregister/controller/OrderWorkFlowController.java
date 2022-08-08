package com.jk.cashregister.controller;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.OrderItem;
import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.domain.dto.OrderDTO;
import com.jk.cashregister.domain.dto.OrderItemDTO;
import com.jk.cashregister.repository.OrderItemRepository;
import com.jk.cashregister.repository.StockRepository;
import com.jk.cashregister.service.OrderService;
import com.jk.cashregister.service.OrderWorkflowService;
import com.jk.cashregister.util.Paging;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.jk.cashregister.util.Strings.*;
import static com.jk.cashregister.util.URLs.*;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderWorkFlowController {
		private final OrderWorkflowService orderWorkFlowService;
		private final StockRepository stockRepository;
		private final OrderService orderService;
		private final OrderItemRepository orderItemRepository;
		private final Paging<OrderItem> paging;
		private final Paging<Stock> stockPaging;

		@PostMapping("/openorder")
		@PreAuthorize("hasAnyRole('ROLE_SENIOR_CASHIER','ROLE_CASHIER')")
		public String openingNewOrder(@Valid @ModelAttribute OrderDTO orderDTO, Model model) {
				Order order = orderWorkFlowService.openNewOrder(orderDTO);
				model.addAttribute("newOrder", order);
				return REDIRECT + ORDER_ROOT + "/" + order.getId() + "/openorder";
		}

		@GetMapping("/{id}/openorder")
		@PreAuthorize("hasAnyRole('ROLE_SENIOR_CASHIER','ROLE_CASHIER')")
		public String addNewOrderView(@RequestParam(value = "page", defaultValue = "1") int page,
																	@RequestParam(value = "size", defaultValue = "3") int size,
																	@PathVariable Long id, Model model) {
				Order newOrder = orderService.getOrderById(id);
				model.addAttribute("newOrder", newOrder);

				List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(id);
				Page<OrderItem> pageToReturn = paging.getPage(page, orderItems, size);

				model.addAttribute("orderItemsForOrder", pageToReturn.getContent());
				model.addAttribute(CURRENT_PAGE, page);
				model.addAttribute(PREVIOUS_PAGE, page - 1);
				model.addAttribute(NEXT_PAGE, page + 1);
				model.addAttribute(NUMBER_OF_PAGES, pageToReturn.getTotalPages());

				return ORDER_ROOT + ID_OPENORDER;
		}

		@GetMapping("/{id}/openorder/searchcode")
		@PreAuthorize("hasAnyRole('ROLE_SENIOR_CASHIER','ROLE_CASHIER')")
		public String searchByCode(@RequestParam(value = "page", defaultValue = "1") int page,
															 @RequestParam(value = "size", defaultValue = "5") int size,
															 @PathVariable Long id, Model model, String code) {
				List<Stock> stockListCode;
				if(code != null) {
						stockListCode = stockRepository.findByProductCodePattern(code);
				} else {
						stockListCode = stockRepository.findAll();
				}
				Order openedOrder = orderService.getOrderById(id);
				Page<Stock> pageToReturn = stockPaging.getPage(page, stockListCode, size);

				model.addAttribute("stockList", pageToReturn.getContent());
				model.addAttribute(CURRENT_PAGE, page);
				model.addAttribute(PREVIOUS_PAGE, page - 1);
				model.addAttribute(NEXT_PAGE, page + 1);
				model.addAttribute(NUMBER_OF_PAGES, pageToReturn.getTotalPages());

				model.addAttribute("openedOrder", openedOrder);
				model.addAttribute("orderItemDTO", new OrderItemDTO());
				return ORDER_ROOT + ID_OPENORDER + "/searchcode";
		}
		@GetMapping("/{id}/openorder/searchname")
		@PreAuthorize("hasAnyRole('ROLE_SENIOR_CASHIER','ROLE_CASHIER')")
		public String searchByName(@RequestParam(value = "page", defaultValue = "1") int page,
															 @RequestParam(value = "size", defaultValue = "5") int size,
															 @PathVariable Long id, Model model, String name) {
				List<Stock> stockListName;
				if(name != null) {
						stockListName = stockRepository.findByProductNamePattern(name);
				} else {
						stockListName = stockRepository.findAll();
				}
				Order openedOrder = orderService.getOrderById(id);
				Page<Stock> pageToReturn = stockPaging.getPage(page, stockListName, size);

				model.addAttribute("stockList", pageToReturn.getContent());
				model.addAttribute(CURRENT_PAGE, page);
				model.addAttribute(PREVIOUS_PAGE, page - 1);
				model.addAttribute(NEXT_PAGE, page + 1);
				model.addAttribute(NUMBER_OF_PAGES, pageToReturn.getTotalPages());

				model.addAttribute("openedOrder", openedOrder);
				model.addAttribute("orderItemDTO", new OrderItemDTO());
				return ORDER_ROOT + ID_OPENORDER + "/searchname";
		}

		@GetMapping("/{id}/closeorder")
		@PreAuthorize("hasAnyRole('ROLE_SENIOR_CASHIER','ROLE_CASHIER')")
		public String closeOrder(@PathVariable Long id, Model model) {
				orderWorkFlowService.closeNewOrder(id);
				return REDIRECT + ORDER_ROOT;
		}
}
