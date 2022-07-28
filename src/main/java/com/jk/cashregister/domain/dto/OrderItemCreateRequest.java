package com.jk.cashregister.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemCreateRequest {

		@Positive(message = "Stock's id must be positive number")
		private Long stockId;

		@Positive(message = "Order's id must be positive number")
		private Long orderId;

		@Positive(message = "Quantity ordered must be positive number")
		private int quantityOrdered;

}
