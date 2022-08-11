package com.jk.cashregister.service.validator;

import com.jk.cashregister.service.exception.NotEnoughQuantityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StockQuantityValidator {
		public void validateOrderedQuantity(int oldQuantity, int newQuantity) {
				if (oldQuantity < newQuantity) {
						if (log.isDebugEnabled()) {
								log.warn("Attempt to order more than currently in stock!");
						}
						throw new NotEnoughQuantityException("There is not enough stock left in warehouse");
				}
		}
}
