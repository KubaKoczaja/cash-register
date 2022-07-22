package com.jk.cashregister.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Data
@NoArgsConstructor
public class StockCreateRequest {

		private String productCode;
		private String productName;
		private int quantity;
		private int price;
}
