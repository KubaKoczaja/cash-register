package com.jk.cashregister.controller;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.OrderItem;
import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.domain.dto.OrderDTO;
import com.jk.cashregister.domain.dto.OrderItemDTO;
import com.jk.cashregister.repository.OrderItemRepository;
import com.jk.cashregister.repository.OrderRepository;
import com.jk.cashregister.service.OrderItemService;
import com.jk.cashregister.service.OrderService;
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
public class OrderController {
		private final OrderService orderService;
		private final OrderRepository orderRepository;
		private final OrderItemRepository orderItemRepository;
		private final OrderItemService orderItemService;
		private final Paging<OrderItem> paging;

		@GetMapping
		public String viewAllStock(@RequestParam(value = "page", defaultValue = "1") int page,
															 @RequestParam(value = "size", defaultValue = "5") int size,
															 Model model) {
				Page<Order> allOrders = orderService.getAllOrders(page, size);
				model.addAttribute("allOrders", allOrders.getContent());
				model.addAttribute(CURRENT_PAGE, page);
				model.addAttribute(PREVIOUS_PAGE, page - 1);
				model.addAttribute(NEXT_PAGE, page + 1);
				model.addAttribute(NUMBER_OF_PAGES, allOrders.getTotalPages());
				model.addAttribute("orderDTO", new OrderDTO());
				return ORDER_ROOT;
		}

		@GetMapping("/{id}/details")
		public String viewStockById(@RequestParam(value = "page", defaultValue = "1") int page,
																@RequestParam(value = "size", defaultValue = "3") int size,
																@PathVariable Long id, Model model) {
				Order orderDetails = orderService.getOrderById(id);
				model.addAttribute("orderDetails", orderDetails);

				List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(id);
				Page<OrderItem> pageToReturn = paging.getPage(page, orderItems, size);

				model.addAttribute("orderItemsForOrder", pageToReturn.getContent());
				model.addAttribute(CURRENT_PAGE, page);
				model.addAttribute(PREVIOUS_PAGE, page - 1);
				model.addAttribute(NEXT_PAGE, page + 1);
				model.addAttribute(NUMBER_OF_PAGES, pageToReturn.getTotalPages());
				return ORDER_ROOT + ID_DETAILS;
		}

		@PostMapping("/{id}/delete")
		@PreAuthorize("hasRole('ROLE_SENIOR_CASHIER')")
		public String deleteOrder(@PathVariable long id) {
				// log
				orderService.deleteOrderWithItems(id);
				//log out
				return REDIRECT + ORDER_ROOT;
		}
		@PostMapping("/{id}/details/{itemId}/deleteitem")
		@PreAuthorize("hasRole('ROLE_SENIOR_CASHIER')")
		public String deleteItemFromOrder(@PathVariable(name = "id") Long orderId, @PathVariable(name = "itemId") Long itemId) {
				orderItemService.deleteOrderItemFromOrder(itemId);
				return REDIRECT + ORDER_ROOT + ID_DETAILS;
		}

		@GetMapping("/{id}/details/{itemId}/updateitem")
		@PreAuthorize("hasRole('ROLE_SENIOR_CASHIER')")
		public String getItemUpdateView(@PathVariable(name = "id") Long orderId,@PathVariable(name = "itemId") Long id, Model model) {
				Stock orderItemStock = orderItemService.getOrderItemById(id).getStock();
				OrderItemDTO orderItemDTOToUpdate = orderItemService.getOrderItemDTOById(id);
				model.addAttribute("orderItemDTOToUpdate", orderItemDTOToUpdate);
				model.addAttribute("orderItemStock", orderItemStock);
				model.addAttribute("orderId", orderId);
				model.addAttribute("itemId", id);
				return ORDER_ROOT + ID_DETAILS + "/{itemId}/updateitem";
		}

		@PostMapping("/{id}/details/{itemId}/updateitem")
		@PreAuthorize("hasRole('ROLE_SENIOR_CASHIER')")
		public String updateOrderItem(@PathVariable(name = "itemId") Long itemId, @PathVariable(name = "id") Long id, @Valid OrderItemDTO orderItemDTOToUpdate) {
				orderItemService.updateOrderItem(itemId, orderItemDTOToUpdate);
				return REDIRECT + ORDER_ROOT + ID_DETAILS;
		}
}
