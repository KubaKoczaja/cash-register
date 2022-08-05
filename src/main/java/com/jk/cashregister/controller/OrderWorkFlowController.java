package com.jk.cashregister.controller;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.OrderItem;
import com.jk.cashregister.domain.dto.OrderDTO;
import com.jk.cashregister.service.OrderItemService;
import com.jk.cashregister.service.OrderWorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderWorkFlowController {
		private static final String ORDER_ROOT = "/order";
		private final OrderWorkflowService orderWorkFlowService;
		private final OrderItemService orderItemService;

		@PostMapping("/openorder")
		public String openingNewOrder(@Valid @ModelAttribute OrderDTO orderDTO, Model model) {
				Order order = orderWorkFlowService.openNewOrder(orderDTO);
				model.addAttribute("newOrder", order);
				return ORDER_ROOT + "/openorder";
		}

		@GetMapping("/openorder")
		public String addNewOrderView(Model model) {
				Order newOrder = (Order) model.getAttribute("newOrder");
				List<OrderItem> orderItemsForOrder = orderItemService.getAllOrderItems(newOrder.getId());
				model.addAttribute("orderItemsForOrder", orderItemsForOrder);
				return ORDER_ROOT + "/openorder";
		}

		@GetMapping("/{id}/openorder/additem")
		public String addItem(){
				return ORDER_ROOT + "/{id}/openorder/additem";
		}

//		@PostMapping("/{id}/openorder/additem")

}
