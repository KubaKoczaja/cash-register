package com.jk.cashregister.controller;

import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.domain.dto.StockCreateRequest;
import com.jk.cashregister.repository.StockRepository;
import com.jk.cashregister.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/stock")
public class StockController {

		private final StockService stockService;
		private final StockRepository stockRepository;

		@Autowired
		public StockController(StockService stockService, StockRepository stockRepository) {
				this.stockService = stockService;
				this.stockRepository = stockRepository;
		}

		@GetMapping
		public String viewAllStock(Model model) {
				List<Stock> allStock = stockService.getAllStock();
				model.addAttribute("allStock", allStock);
				return "/stock";
		}

		@PostMapping("/addstock")
		public RedirectView addingNewStock(@RequestBody StockCreateRequest stockCreateRequest) {
				stockService.createStock(stockCreateRequest);
				return new RedirectView("/stock");
		}

		@GetMapping("/details/{id}")
		public String viewStockById(@PathVariable long id, Model model) {
				Stock stock = stockService.getById(id);
				model.addAttribute("stockItem", stock);
				return "/stock/details";
		}

		@GetMapping("/addstock")
		public String addNewStockView() {
				return "/stock/addstock";
		}

		@DeleteMapping("/details/{id}")
		public RedirectView deleteStock(@PathVariable long id) {
				stockRepository.deleteById(id);
				return new RedirectView("/stock");
		}

		@GetMapping("/update/{id}")
		public String viewUpdateStock(@PathVariable long id, Model model) {
				Stock stockToUpdate = stockService.getById(id);
				model.addAttribute("stockToUpdate", stockToUpdate);
				return "/stock/update/{id}";
		}

		@PutMapping("/update")
		public RedirectView updateStock(@RequestBody StockCreateRequest stockCreateRequest, Model model) {
				Stock stock = (Stock) model.getAttribute("stockToUpdate");
				stockService.updateStock(stockCreateRequest,stock);
				return new RedirectView("/stock");
		}
}
