package com.jk.cashregister.service.validator;

import com.jk.cashregister.domain.dto.StockCreateRequest;
import com.jk.cashregister.service.exception.InvalidProductCodeException;
import com.jk.cashregister.service.exception.InvalidProductNameException;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class StockCreateRequestValidator {
		public void validate(StockCreateRequest request) {
				if (request.getProductCode() == null || request.getProductCode().isBlank()) {
						throw new InvalidProductCodeException();
				}
				if (request.getProductName() == null || request.getProductName().isBlank()) {
						throw new InvalidProductNameException();
				}
				if (request.getQuantity() < 0 || request.getPrice() < 0) {
						throw new IllegalArgumentException();
				}
		}
}
