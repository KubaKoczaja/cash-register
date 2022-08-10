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

		@NotBlank(message = "{productCode.notblank}")
		private String productCode;
		@NotBlank(message = "{productName.notblank}")
		private String productName;
		@PositiveOrZero(message = "{quantity.positiveorzero}")
		private int quantity;
		@Positive(message = "{price.positive}")
		private int price;
}
