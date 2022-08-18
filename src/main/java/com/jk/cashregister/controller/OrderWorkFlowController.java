package com.jk.cashregister.controller;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.OrderItem;
import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.repository.OrderItemRepository;
import com.jk.cashregister.service.OrderService;
import com.jk.cashregister.service.OrderWorkflowService;
import com.jk.cashregister.service.StockService;
import com.jk.cashregister.service.dto.OrderDTO;
import com.jk.cashregister.service.dto.OrderItemDTO;
import com.jk.cashregister.service.dto.SearchDTO;
import com.jk.cashregister.util.Paging;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import static com.jk.cashregister.util.Strings.*;
import static com.jk.cashregister.util.URLs.*;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
@Slf4j
public class OrderWorkFlowController {
		private final OrderWorkflowService orderWorkFlowService;
		private final StockService stockService;
		private final OrderService orderService;
		private final OrderItemRepository orderItemRepository;
		private final Paging<OrderItem> paging;
		private final Paging<Stock> stockPaging;

		@PostMapping("/openorder")
		@PreAuthorize("hasAnyRole('ROLE_SENIOR_CASHIER','ROLE_CASHIER')")
		public String openingNewOrder(@Valid @ModelAttribute OrderDTO orderDTO, Model model) {
				log.info("Opening new order");
				Order order = orderWorkFlowService.openNewOrder(orderDTO);
				model.addAttribute("newOrder", order);
				return REDIRECT + ORDER_ROOT + "/" + order.getId() + "/editorder";
		}

		@GetMapping("/{id}/editorder")
		@PreAuthorize("hasAnyRole('ROLE_SENIOR_CASHIER','ROLE_CASHIER')")
		public String addNewOrderView(@RequestParam(value = "page", defaultValue = "1") int page,
																	@RequestParam(value = "size", defaultValue = "3") int size,
																	@PathVariable Long id, Model model) {
				Order newOrder = orderService.getOrderById(id);
				model.addAttribute("newOrder", newOrder);
				model.addAttribute("searchDTO", new SearchDTO());

				List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(id);
				Page<OrderItem> pageToReturn = paging.getPage(page, orderItems, size);

				model.addAttribute("orderItemsForOrder", pageToReturn.getContent());
				model.addAttribute(CURRENT_PAGE, page);
				model.addAttribute(PREVIOUS_PAGE, page - 1);
				model.addAttribute(NEXT_PAGE, page + 1);
				model.addAttribute(NUMBER_OF_PAGES, pageToReturn.getTotalPages());

				return ORDER_ROOT + ID_EDITORDER;
		}

		@PostMapping("/{id}/editorder/search")
		@PreAuthorize("hasAnyRole('ROLE_SENIOR_CASHIER','ROLE_CASHIER')")
		public String searchByPattern(@ModelAttribute SearchDTO searchDTO, HttpSession session) {
				session.setAttribute("searchDTO", searchDTO);
				return REDIRECT + ORDER_ROOT + ID_EDITORDER + "/search";
		}

		@GetMapping("/{id}/editorder/search")
		public String getSearchView(@RequestParam(value = "page", defaultValue = "1") int page,
																@RequestParam(value = "size", defaultValue = "5") int size,
																@PathVariable Long id, Model model, HttpSession session) {
				SearchDTO searchDTO = (SearchDTO) session.getAttribute("searchDTO");

				List<Stock> stockList = stockService.getAllUsingSearch(searchDTO);
				Order openedOrder = orderService.getOrderById(id);
				Page<Stock> pageToReturn = stockPaging.getPage(page, stockList, size);

				model.addAttribute("stockListPage", pageToReturn.getContent());
				model.addAttribute(CURRENT_PAGE, page);
				model.addAttribute(PREVIOUS_PAGE, page - 1);
				model.addAttribute(NEXT_PAGE, page + 1);
				model.addAttribute(NUMBER_OF_PAGES, pageToReturn.getTotalPages());
				model.addAttribute("openedOrder", openedOrder);
				model.addAttribute("orderItemDTO", new OrderItemDTO());
				return ORDER_ROOT + ID_EDITORDER + "/search";
		}

		@GetMapping("/{id}/closeorder")
		@PreAuthorize("hasAnyRole('ROLE_SENIOR_CASHIER','ROLE_CASHIER')")
		public String closeOrder(@PathVariable Long id, Model model) {
				log.info("Closing new order");
				orderWorkFlowService.closeNewOrder(id);
				log.info("New order closed");
				return REDIRECT + ORDER_ROOT;
		}

		@PostMapping("/{id}/deleteneworder")
		@PreAuthorize("hasAnyRole('ROLE_SENIOR_CASHIER','ROLE_CASHIER')")
		public String deleteNewOrder(@PathVariable Long id) {
				orderService.deleteOrderWithItems(id);
				return REDIRECT + ORDER_ROOT;
		}

}
