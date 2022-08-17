package com.jk.cashregister.service.exception;

public class NotEnoughQuantityException extends CustomException{
		public NotEnoughQuantityException(String message) {
				super(message);
		}

		@Override
		public String getBundledKey() {
				return "not_enough_stock";
		}
}
