package com.jk.cashregister.controller;

import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.domain.dto.StockCreateRequest;
import com.jk.cashregister.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class StockController {

		private final StockService stockService;

		@Autowired
		public StockController(StockService stockService) {
				this.stockService = stockService;
		}

		@GetMapping("/stock")
		public String viewAllStock(Model model) {
				List<Stock> allStock = stockService.getAllStock();
				model.addAttribute("allStock", allStock);
				return "/stock";
		}

		@PostMapping("/stock/addstock")
		public RedirectView addingNewStock(@RequestBody StockCreateRequest stockCreateRequest) {
				stockService.createStock(stockCreateRequest);
				return new RedirectView("/stock");
		}

		@GetMapping("/stock/details/{id}")
		public String viewStockById(@PathVariable long id, Model model) {
				Stock stock = stockService.getById(id);
				model.addAttribute("stockItem", stock);
				return "/stock/details";
		}

		@GetMapping("/stock/addstock")
		public String addNewStockView() {
				return "/stock/addstock";
		}



}
