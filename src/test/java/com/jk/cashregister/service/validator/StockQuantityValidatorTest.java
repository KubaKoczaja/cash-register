package com.jk.cashregister.service.validator;

import com.jk.cashregister.service.exception.NotEnoughQuantityException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StockQuantityValidatorTest {
		private StockQuantityValidator stockQuantityValidator = new StockQuantityValidator();
		private int oldQuantity;
		private int newQuantity;


		@Test
		void shouldReturnTrueIfQuantitiesAreCorrect() {
				oldQuantity = 20;
				newQuantity = 10;

				assertTrue(stockQuantityValidator.validateOrderedQuantity(oldQuantity, newQuantity));
		}

		@Test
		void shouldThrowExceptionWhenQuantitiesAreIncorrect() {
				oldQuantity = 15;
				newQuantity = 20;

				assertThrows(NotEnoughQuantityException.class, () -> stockQuantityValidator.validateOrderedQuantity(oldQuantity, newQuantity));
		}

}