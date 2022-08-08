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

import javax.validation.Valid;

import static com.jk.cashregister.util.Strings.*;
import static com.jk.cashregister.util.URLs.REDIRECT;
import static com.jk.cashregister.util.URLs.STOCK_ROOT;

@Controller
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {

		private final StockService stockService;
		private final OrderItemRepository orderItemRepository;

		@GetMapping
		public String viewAllStock(@RequestParam(value = "page", defaultValue = "1") int page,
															 @RequestParam(value = "size", defaultValue = "5") int size,
															 Model model) {
				Page<Stock> allStock = stockService.getAllStock(page,size);
				model.addAttribute("allStock", allStock.getContent());
				model.addAttribute(CURRENT_PAGE, page);
				model.addAttribute(PREVIOUS_PAGE, page - 1);
				model.addAttribute(NEXT_PAGE, page + 1);
				model.addAttribute(NUMBER_OF_PAGES, allStock.getTotalPages());
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
		public String addingNewStock(@Valid StockDTO stockDTO) {
				stockService.createStock(stockDTO);
				return REDIRECT + STOCK_ROOT;
		}

		@GetMapping("/addstock")
		@PreAuthorize("hasRole('ROLE_COMMODITY_EXPERT')")
		public String addNewStockView(Model model) {
				model.addAttribute("stockDTO", new StockDTO());
				return STOCK_ROOT + "/addstock";
		}

		@PostMapping("/{id}/delete")
		@PreAuthorize("hasRole('ROLE_COMMODITY_EXPERT')")
		public String deleteStock(@PathVariable long id) {
				// check if stock item is somewhere in order
				if (!orderItemRepository.findAllByStockId(id).isEmpty()) {
						throw new StockDeletingException("You can't delete stock in active order");
				}
				stockService.deleteStockById(id);
				return REDIRECT + STOCK_ROOT;
		}

		@GetMapping("/{id}/update")
		@PreAuthorize("hasRole('ROLE_COMMODITY_EXPERT')")
		public String viewUpdateStock(@PathVariable long id, Model model) {
				StockDTO stockDTOToUpdate = stockService.getStockDTOFromId(id);
				model.addAttribute("stockDTOToUpdate", stockDTOToUpdate);
				model.addAttribute("stockId", id);
				return STOCK_ROOT + "/{id}/update";
		}

		@PostMapping("{id}/update")
		@PreAuthorize("hasRole('ROLE_COMMODITY_EXPERT')")
		public String updateStock(@PathVariable Long id, @Valid StockDTO stockDTO, Model model) {
				stockService.updateStock(stockDTO,id);
				return REDIRECT + STOCK_ROOT;
		}
}
