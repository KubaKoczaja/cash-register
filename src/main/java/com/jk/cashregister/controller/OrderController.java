package com.jk.cashregister.controller;

import com.jk.cashregister.domain.Order;
import com.jk.cashregister.domain.dto.OrderDTO;
import com.jk.cashregister.repository.OrderItemRepository;
import com.jk.cashregister.repository.OrderRepository;
import com.jk.cashregister.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

		public static final String ORDER_ROOT = "/order";
		private final OrderService orderService;

		private final OrderRepository orderRepository;
		private final OrderItemRepository orderItemRepository;

		//TODO add hidden inputs for OrderDTO
		@GetMapping(name = "?page={page}")
		public String viewAllStock(@RequestParam(defaultValue = "1") int page, Model model) {
				List<Order> allOrders = orderService.getAllOrders(page);
				model.addAttribute("allOrders", allOrders);
				model.addAttribute("currentPage", page);
				model.addAttribute("previousPage", page - 1);
				model.addAttribute("nextPage", page + 1);
				model.addAttribute("orderDTO", new OrderDTO());
				return ORDER_ROOT;
		}

		@GetMapping("/{id}/details")
		public String viewStockById(@PathVariable long id, Model model) {
				Order orderDetails = orderService.getOrderById(id);
				model.addAttribute("orderDetails", orderDetails);
				return "/order/{id}/details";
		}

		@Transactional
		@PostMapping("/{id}/delete")
		public RedirectView deleteOrder(@PathVariable long id) {
				orderItemRepository.deleteAllByOrderId(id);
				orderRepository.deleteById(id);
				return new RedirectView(ORDER_ROOT);
		}

//		@GetMapping("/{id}/update")
//		public String viewUpdateStock(@PathVariable long id, Model model) {
//				Stock stockToUpdate = stockService.getById(id);
//				model.addAttribute("stockToUpdate", stockToUpdate);
//				model.addAttribute("stockDTO", new StockDTO());
//				return "/order/{id}/update";
//		}
//
//		@PostMapping("{id}/update")
//		public RedirectView updateStock(@PathVariable Long id, @ModelAttribute StockDTO stockDTO, Model model) {
//				stockService.updateStock(stockDTO,id);
//				return new RedirectView(ORDER_ROOT);
//		}
}
