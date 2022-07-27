package com.jk.cashregister.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class StockCreateRequest {

		@NotBlank
		private String productCode;
		@NotBlank
		private String productName;
		@PositiveOrZero
		private int quantity;
		@Positive
		private int price;
}
