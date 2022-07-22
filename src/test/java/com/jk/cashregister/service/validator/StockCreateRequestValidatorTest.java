package com.jk.cashregister.service.validator;

import com.jk.cashregister.domain.dto.StockCreateRequest;
import com.jk.cashregister.service.exception.InvalidProductCodeException;
import com.jk.cashregister.service.exception.InvalidProductNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class StockCreateRequestValidatorTest {

		private StockCreateRequest request;
		private final StockCreateRequestValidator stockCreateRequestValidator = new StockCreateRequestValidator();

		@BeforeEach
		void setUp() {
				request = new StockCreateRequest("test","test", 1, 1);
		}

		@ParameterizedTest
		@NullSource
		@ValueSource(strings = { "", "\n", "   ", "\t" })
		void shouldThrowExceptionIfProductCodeIsNull(String productCode) {
				request.setProductCode(productCode);
				assertThrows(InvalidProductCodeException.class, () -> stockCreateRequestValidator.validate(request));
		}

		@ParameterizedTest
		@NullSource
		@ValueSource(strings = { "", "\n", "   ", "\t" })
		void shouldReturnExceptionIfProductNameIsNull(String productName) {
				request.setProductName(productName);
				assertThrows(InvalidProductNameException.class, () -> stockCreateRequestValidator.validate(request));
		}

		@ParameterizedTest
		@ValueSource(ints = { -1, -24, -100 })
		void shouldThrowExceptionWhenQuantityIsNegative(int quantity) {
				request.setQuantity(quantity);
				assertThrows(IllegalArgumentException.class, () -> stockCreateRequestValidator.validate(request));
		}

		@ParameterizedTest
		@ValueSource(ints = { -1, -24, -100 })
		void shouldThrowExceptionWhenPriceIsNegative(int price) {
				request.setQuantity(price);
				assertThrows(IllegalArgumentException.class, () -> stockCreateRequestValidator.validate(request));
		}
}