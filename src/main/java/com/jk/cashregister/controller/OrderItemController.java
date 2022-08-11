package com.jk.cashregister.controller;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.domain.dto.OrderItemDTO;
import com.jk.cashregister.service.OrderItemService;
import com.jk.cashregister.service.OrderWorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static com.jk.cashregister.util.URLs.*;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderItemController {

		private final OrderItemService orderItemService;
		private final OrderWorkflowService orderWorkflowService;


		@PostMapping("/{id}/editorder/additem")
		@PreAuthorize("hasAnyRole('ROLE_SENIOR_CASHIER','ROLE_CASHIER')")
		public String addItem(@PathVariable(name = "id") Long orderId, Model model, @Valid OrderItemDTO orderItemDTO){
				Order newOrder = orderWorkflowService.addNewOrderItemToOrder(orderItemDTO,orderId);
				model.addAttribute("newOrder", newOrder);
				return REDIRECT + ORDER_ROOT + ID_EDITORDER;
		}

		@GetMapping("/{id}/editorder/{itemId}/updateitem")
		@PreAuthorize("hasAnyRole('ROLE_SENIOR_CASHIER','ROLE_CASHIER')")
		public String getItemUpdateForNewOrderView(@PathVariable(name = "id") Long orderId,@PathVariable(name = "itemId") Long id, Model model) {
				Stock orderItemStock = orderItemService.getOrderItemById(id).getStock();
				OrderItemDTO orderItemDTOToUpdate = orderItemService.getOrderItemDTOById(id);
				model.addAttribute("orderItemDTOToUpdate", orderItemDTOToUpdate);
				model.addAttribute("orderItemStock", orderItemStock);
				model.addAttribute("orderId", orderId);
				model.addAttribute("itemId", id);
				return ORDER_ROOT + ID_EDITORDER + "/{itemId}/update";
		}

		@PostMapping("/{id}/editorder/{itemId}/updateitem")
		@PreAuthorize("hasAnyRole('ROLE_SENIOR_CASHIER','ROLE_CASHIER')")
		public String updateOrderItemFromNewOrder(@PathVariable(name = "itemId") Long itemId, @PathVariable(name = "id") Long id, @Valid OrderItemDTO orderItemDTOToUpdate) {
				orderItemService.updateOrderItem(itemId, orderItemDTOToUpdate);
				return REDIRECT + ORDER_ROOT + ID_EDITORDER;
		}

		@PostMapping("/{id}/editorder/{itemId}/deleteitem")
		@PreAuthorize("hasAnyRole('ROLE_SENIOR_CASHIER','ROLE_CASHIER')")
		public String deleteItemFromNewOrder(@PathVariable(name = "id") Long orderId, @PathVariable(name = "itemId") Long itemId) {
				orderItemService.deleteOrderItemFromOrder(itemId);
				return REDIRECT + ORDER_ROOT + ID_EDITORDER;
		}
}
