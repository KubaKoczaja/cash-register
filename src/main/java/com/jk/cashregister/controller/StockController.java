package com.jk.cashregister.controller;

import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.domain.dto.StockDTO;
import com.jk.cashregister.service.StockService;
import com.jk.cashregister.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {

		public static final String STOCK_ROOT = "/stock";
		private final StockService stockService;
		private final UserService userService;


		@GetMapping(name = "?page={page}")
		public String viewAllStock(@RequestParam(defaultValue = "1") int page, Model model) {
				List<Stock> allStock = stockService.getAllStock(page);
				model.addAttribute("allStock", allStock);
				model.addAttribute("currentPage", page);
				model.addAttribute("previousPage", page - 1);
				model.addAttribute("nextPage", page + 1);
				return STOCK_ROOT;
		}

		@GetMapping("/{id}/details")
		public String viewStockById(@PathVariable long id, Model model) {
				Stock stock = stockService.getById(id);
				model.addAttribute("stockItem", stock);
				return "/stock/{id}/details";
		}
		@PostMapping("/addstock")
		public RedirectView addingNewStock(@Valid @ModelAttribute StockDTO stockDTO) {
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
		public RedirectView deleteStock(@PathVariable long id) {
				stockService.deleteStockById(id);
				return new RedirectView(STOCK_ROOT);
		}

		@GetMapping("/{id}/update")
		public String viewUpdateStock(@PathVariable long id, Model model) {
				Stock stockToUpdate = stockService.getById(id);
				model.addAttribute("stockToUpdate", stockToUpdate);
				model.addAttribute("stockDTO", new StockDTO());
				return "/stock/{id}/update";
		}

		@PostMapping("{id}/update")
		public RedirectView updateStock(@PathVariable Long id, @ModelAttribute StockDTO stockDTO, Model model) {
				stockService.updateStock(stockDTO,id);
				return new RedirectView(STOCK_ROOT);
		}
}
