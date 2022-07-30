package com.jk.cashregister.controller;

import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.domain.dto.StockDTO;
import com.jk.cashregister.service.StockService;
import com.jk.cashregister.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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

		@PostMapping("/addstock")
		public RedirectView addingNewStock(@RequestBody StockDTO stockDTO) {
				stockService.createStock(stockDTO);
				return new RedirectView(STOCK_ROOT);
		}

		@GetMapping("/{id}/details")
		public String viewStockById(@PathVariable long id, Model model) {
				Stock stock = stockService.getById(id);
				model.addAttribute("stockItem", stock);
				return "/stock/{id}/details";
		}

		@GetMapping("/addstock")
		public String addNewStockView() {
				return "/stock/addstock";
		}

		@DeleteMapping("/{id}/delete")
		public RedirectView deleteStock(@PathVariable long id) {
				stockService.deleteStockById(id);
				return new RedirectView(STOCK_ROOT);
		}

		@GetMapping("/{id}/update")
		public String viewUpdateStock(@PathVariable long id, Model model) {
				Stock stockToUpdate = stockService.getById(id);
				model.addAttribute("stockToUpdate", stockToUpdate);
				return "/stock/{id}/update";
		}

		@PutMapping("{id}/update")
		public RedirectView updateStock(@PathVariable Long id, @RequestBody StockDTO stockDTO, Model model) {
				stockService.updateStock(stockDTO,id);
				return new RedirectView(STOCK_ROOT);
		}
}
