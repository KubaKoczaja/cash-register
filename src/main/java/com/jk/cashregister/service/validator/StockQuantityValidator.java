package com.jk.cashregister.service.validator;

import com.jk.cashregister.service.exception.NotEnoughQuantityException;
import org.springframework.stereotype.Component;

@Component
public class StockQuantityValidator {
		public void validateOrderedQuantity(int oldQuantity, int newQuantity) {
				if (oldQuantity < newQuantity) {
						throw new NotEnoughQuantityException("There is not enough stock left in warehouse");
				}
		}
}
