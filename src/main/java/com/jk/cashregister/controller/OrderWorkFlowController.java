package com.jk.cashregister.controller;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.OrderItem;
import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.domain.dto.OrderDTO;
import com.jk.cashregister.domain.dto.OrderItemDTO;
import com.jk.cashregister.repository.StockRepository;
import com.jk.cashregister.service.OrderItemService;
import com.jk.cashregister.service.OrderService;
import com.jk.cashregister.service.OrderWorkflowService;
import com.jk.cashregister.service.StockService;
import com.jk.cashregister.service.mapper.OrderItemDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderWorkFlowController {
		private static final String ORDER_ROOT = "/order";
		private final OrderWorkflowService orderWorkFlowService;
		private final OrderItemService orderItemService;
		private final StockRepository stockRepository;
		private final OrderService orderService;
		private final StockService stockService;
		private final OrderItemDTOMapper orderItemDTOMapper;

		@PostMapping("/openorder")
		@PreAuthorize("hasAnyRole('ROLE_SENIOR_CASHIER','ROLE_CASHIER')")
		public String openingNewOrder(@Valid @ModelAttribute OrderDTO orderDTO, Model model) {
				Order order = orderWorkFlowService.openNewOrder(orderDTO);
				model.addAttribute("newOrder", order);
				return "redirect:" + ORDER_ROOT + "/" + order.getId() + "/openorder";
		}

		@GetMapping("/{id}/openorder")
		@PreAuthorize("hasAnyRole('ROLE_SENIOR_CASHIER','ROLE_CASHIER')")
		public String addNewOrderView(@PathVariable Long id, Model model) {
				Order newOrder = orderService.getOrderById(id);
				model.addAttribute("newOrder", newOrder);
				List<OrderItem> orderItemsForOrder = orderItemService.getAllOrderItems(id);
				model.addAttribute("orderItemsForOrder", orderItemsForOrder);
				return ORDER_ROOT + "/{id}/openorder";
		}

		@GetMapping("/{id}/openorder/searchCode")
		@PreAuthorize("hasAnyRole('ROLE_SENIOR_CASHIER','ROLE_CASHIER')")
		public String searchByCode(@PathVariable Long id, Model model, String code) {
				List<Stock> stockListCode;
				if(code != null) {
						stockListCode = stockRepository.findByProductCodePattern(code);
				} else {
						stockListCode = stockRepository.findAll();
				}
				Order openedOrder = orderService.getOrderById(id);
				model.addAttribute("stockList", stockListCode);
				model.addAttribute("openedOrder", openedOrder);
				model.addAttribute("orderItemDTO", new OrderItemDTO());
				return ORDER_ROOT + "/{id}/openorder/search";
		}
		@GetMapping("/{id}/openorder/searchName")
		@PreAuthorize("hasAnyRole('ROLE_SENIOR_CASHIER','ROLE_CASHIER')")
		public String searchByName(@PathVariable Long id, Model model, String name) {
				List<Stock> stockListName;
				if(name != null) {
						stockListName = stockRepository.findByProductNamePattern(name);
				} else {
						stockListName = stockRepository.findAll();
				}
				Order openedOrder = orderService.getOrderById(id);
				model.addAttribute("stockList", stockListName);
				model.addAttribute("openedOrder", openedOrder);
				model.addAttribute("orderItemDTO", new OrderItemDTO());
				return ORDER_ROOT + "/{id}/openorder/search";
		}

		@PostMapping("/{id}/openorder/additem")
		@PreAuthorize("hasAnyRole('ROLE_SENIOR_CASHIER','ROLE_CASHIER')")
		public String addItem(@PathVariable(name = "id") Long orderId, Model model, OrderItemDTO orderItemDTO){
				OrderItem orderItem = orderItemDTOMapper.map(orderItemDTO);

				Order order = orderService.getOrderById(orderId);
				orderItem.setOrder(order);

				model.addAttribute("newOrder", order);
				orderWorkFlowService.addNewOrderItemToOrder(orderItem);

				return "redirect:" + ORDER_ROOT + "/{id}/openorder";
		}

		@GetMapping("/{id}/closeorder")
		@PreAuthorize("hasAnyRole('ROLE_SENIOR_CASHIER','ROLE_CASHIER')")
		public String closeOrder(@PathVariable Long id, Model model) {
				orderWorkFlowService.closeNewOrder(id);
				return "redirect:" + ORDER_ROOT;
		}
}
