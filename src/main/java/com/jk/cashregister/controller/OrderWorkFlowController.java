package com.jk.cashregister.controller;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.OrderItem;
import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.domain.dto.OrderDTO;
import com.jk.cashregister.domain.dto.OrderItemDTO;
import com.jk.cashregister.repository.OrderItemRepository;
import com.jk.cashregister.repository.StockRepository;
import com.jk.cashregister.service.OrderItemService;
import com.jk.cashregister.service.OrderService;
import com.jk.cashregister.service.OrderWorkflowService;
import com.jk.cashregister.service.mapper.OrderItemDTOMapper;
import com.jk.cashregister.util.Paging;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.jk.cashregister.util.URLs.*;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderWorkFlowController {
		private final OrderWorkflowService orderWorkFlowService;
		private final OrderItemService orderItemService;
		private final StockRepository stockRepository;
		private final OrderService orderService;
		private final OrderItemDTOMapper orderItemDTOMapper;
		private final OrderItemRepository orderItemRepository;
		private final Paging<OrderItem> paging;

		@PostMapping("/openorder")
		@PreAuthorize("hasAnyRole('ROLE_SENIOR_CASHIER','ROLE_CASHIER')")
		public String openingNewOrder(@Valid @ModelAttribute OrderDTO orderDTO, Model model) {
				Order order = orderWorkFlowService.openNewOrder(orderDTO);
				model.addAttribute("newOrder", order);
				return REDIRECT + ORDER_ROOT + "/" + order.getId() + "/openorder";
		}

		@GetMapping("/{id}/openorder")
		@PreAuthorize("hasAnyRole('ROLE_SENIOR_CASHIER','ROLE_CASHIER')")
		public String addNewOrderView(@RequestParam("page") Optional<Integer> page, @PathVariable Long id, Model model) {
				Order newOrder = orderService.getOrderById(id);
				model.addAttribute("newOrder", newOrder);

				List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(id);
				int currentPage = page.orElse(1);

				Page<OrderItem> pageToReturn = paging.getPage(currentPage, orderItems, 3);

				model.addAttribute("orderItemsForOrder", pageToReturn.getContent());
				model.addAttribute("currentPage", currentPage);
				model.addAttribute("previousPage", currentPage - 1);
				model.addAttribute("nextPage", currentPage + 1);
				model.addAttribute("numberOfPages", pageToReturn.getTotalPages());

				return ORDER_ROOT + ID_OPENORDER;
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
				return ORDER_ROOT + ID_OPENORDER + "/search";
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
				return ORDER_ROOT + ID_OPENORDER + "/search";
		}

		@PostMapping("/{id}/openorder/additem")
		@PreAuthorize("hasAnyRole('ROLE_SENIOR_CASHIER','ROLE_CASHIER')")
		public String addItem(@PathVariable(name = "id") Long orderId, Model model, OrderItemDTO orderItemDTO){
				OrderItem orderItem = orderItemDTOMapper.mapToOrderItem(orderItemDTO);

				Order order = orderService.getOrderById(orderId);
				orderItem.setOrder(order);

				model.addAttribute("newOrder", order);
				orderWorkFlowService.addNewOrderItemToOrder(orderItem);

				return REDIRECT + ORDER_ROOT + ID_OPENORDER;
		}

		@GetMapping("/{id}/closeorder")
		@PreAuthorize("hasAnyRole('ROLE_SENIOR_CASHIER','ROLE_CASHIER')")
		public String closeOrder(@PathVariable Long id, Model model) {
				orderWorkFlowService.closeNewOrder(id);
				return REDIRECT + ORDER_ROOT;
		}

		@GetMapping("/{id}/openorder/{itemId}/updateitem")
		@PreAuthorize("hasAnyRole('ROLE_SENIOR_CASHIER','ROLE_CASHIER')")
		public String getItemUpdateForNewOrderView(@PathVariable(name = "id") Long orderId,@PathVariable(name = "itemId") Long id, Model model) {
				Stock orderItemStock = orderItemService.getOrderItemById(id).getStock();
				OrderItemDTO orderItemDTOToUpdate = orderItemService.getOrderItemDTOById(id);
				model.addAttribute("orderItemDTOToUpdate", orderItemDTOToUpdate);
				model.addAttribute("orderItemStock", orderItemStock);
				model.addAttribute("orderId", orderId);
				model.addAttribute("itemId", id);
				return ORDER_ROOT + ID_OPENORDER + "/{itemId}/update";
		}

		@PostMapping("/{id}/openorder/{itemId}/updateitem")
		@PreAuthorize("hasAnyRole('ROLE_SENIOR_CASHIER','ROLE_CASHIER')")
		public String updateOrderItemFromNewOrder(@PathVariable(name = "itemId") Long itemId, @PathVariable(name = "id") Long id, @Valid OrderItemDTO orderItemDTOToUpdate) {
				orderItemService.updateOrderItem(itemId, orderItemDTOToUpdate);
				return REDIRECT + ORDER_ROOT + ID_OPENORDER;
		}
		@PostMapping("/{id}/openorder/{itemId}/deleteitem")
		@PreAuthorize("hasAnyRole('ROLE_SENIOR_CASHIER','ROLE_CASHIER')")
		public String deleteItemFromNewOrder(@PathVariable(name = "id") Long orderId, @PathVariable(name = "itemId") Long itemId) {
				orderItemService.deleteOrderItemFromOrder(itemId);
				return REDIRECT + ORDER_ROOT + ID_OPENORDER;
		}
}
