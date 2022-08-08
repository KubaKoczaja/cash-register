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
public class StockDTO {

		@NotBlank(message = "Product code can't be blank!")
		private String productCode;
		@NotBlank(message = "Product name can't be blank!")
		private String productName;
		@PositiveOrZero(message = "Quantity can't be negative number")
		private int quantity;
		@Positive(message = "Price must be bigger than zero")
		private int price;
}
