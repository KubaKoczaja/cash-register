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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

		@GetMapping(name = "?page={page}")
		public String viewAllStock(@RequestParam(defaultValue = "1") int page, Model model) {
				Page<Order> allOrders = orderService.getAllOrders(page);
				model.addAttribute("allOrders", allOrders.getContent());
				model.addAttribute("currentPage", page);
				model.addAttribute("previousPage", page - 1);
				model.addAttribute("nextPage", page + 1);
				model.addAttribute("numberOfPages", allOrders.getTotalPages());
				model.addAttribute("orderDTO", new OrderDTO());
				return ORDER_ROOT;
		}

		@GetMapping("/{id}/details")
		public String viewStockById(@RequestParam("page") Optional<Integer> page, @PathVariable Long id, Model model) {
				Order orderDetails = orderService.getOrderById(id);
				model.addAttribute("orderDetails", orderDetails);

				int currentPage = page.orElse(1);
				List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(id);

				Page<OrderItem> pageToReturn = paging.getPage(currentPage, orderItems, 3);

				model.addAttribute("orderItemsForOrder", pageToReturn.getContent());
				model.addAttribute("currentPage", currentPage);
				model.addAttribute("previousPage", currentPage - 1);
				model.addAttribute("nextPage", currentPage + 1);
				model.addAttribute("numberOfPages", pageToReturn.getTotalPages());
				return ORDER_ROOT + ID_DETAILS;
		}

		@Transactional
		@PostMapping("/{id}/delete")
		@PreAuthorize("hasRole('ROLE_SENIOR_CASHIER')")
		public RedirectView deleteOrder(@PathVariable long id) {
				orderItemRepository.deleteAllByOrderId(id);
				orderRepository.deleteById(id);
				return new RedirectView(ORDER_ROOT);
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
