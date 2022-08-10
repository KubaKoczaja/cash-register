package com.jk.cashregister.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

		@Positive(message = "{stockId.positive}")
		private Long stockId;

		@Positive(message = "{quantityOrdered.positive}")
		private int quantityOrdered;

}
