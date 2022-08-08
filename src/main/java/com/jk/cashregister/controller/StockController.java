package com.jk.cashregister.controller;

import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.domain.dto.StockDTO;
import com.jk.cashregister.repository.OrderItemRepository;
import com.jk.cashregister.service.StockService;
import com.jk.cashregister.service.exception.StockDeletingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {

		public static final String STOCK_ROOT = "/stock";
		private final StockService stockService;
		private final OrderItemRepository orderItemRepository;


		@GetMapping(name = "?page={page}")
		public String viewAllStock(@RequestParam(defaultValue = "1") int page, Model model) {
				Page<Stock> allStock = stockService.getAllStock(page);
				model.addAttribute("allStock", allStock.getContent());
				model.addAttribute("currentPage", page);
				model.addAttribute("previousPage", page - 1);
				model.addAttribute("nextPage", page + 1);
				model.addAttribute("numberOfPages", allStock.getTotalPages());
				return STOCK_ROOT;
		}

		@GetMapping("/{id}/details")
		public String viewStockById(@PathVariable long id, Model model) {
				Stock stock = stockService.getStockById(id);
				model.addAttribute("stockItem", stock);
				return STOCK_ROOT + "/{id}/details";
		}
		@PostMapping("/addstock")
		@PreAuthorize("hasRole('ROLE_COMMODITY_EXPERT')")
		public RedirectView addingNewStock(@Valid StockDTO stockDTO) {
				stockService.createStock(stockDTO);
				return new RedirectView(STOCK_ROOT);
		}

		@GetMapping("/addstock")
		@PreAuthorize("hasRole('ROLE_COMMODITY_EXPERT')")
		public String addNewStockView(Model model) {
				model.addAttribute("stockDTO", new StockDTO());
				return "/stock/addstock";
		}

		@PostMapping("/{id}/delete")
		@PreAuthorize("hasRole('ROLE_COMMODITY_EXPERT')")
		public RedirectView deleteStock(@PathVariable long id) {
				// check if stock item is somewhere in order
				if (!orderItemRepository.findAllByStockId(id).isEmpty()) {
						throw new StockDeletingException("You can't delete stock in active order");
				}
				stockService.deleteStockById(id);
				return new RedirectView(STOCK_ROOT);
		}

		@GetMapping("/{id}/update")
		@PreAuthorize("hasRole('ROLE_COMMODITY_EXPERT')")
		public String viewUpdateStock(@PathVariable long id, Model model) {
				StockDTO stockDTOToUpdate = stockService.getStockDTOFromId(id);
				model.addAttribute("stockDTOToUpdate", stockDTOToUpdate);
				model.addAttribute("stockId", id);
				return "/stock/{id}/update";
		}

		@PostMapping("{id}/update")
		@PreAuthorize("hasRole('ROLE_COMMODITY_EXPERT')")
		public RedirectView updateStock(@PathVariable Long id, @Valid StockDTO stockDTO, Model model) {
				stockService.updateStock(stockDTO,id);
				return new RedirectView(STOCK_ROOT);
		}
}
