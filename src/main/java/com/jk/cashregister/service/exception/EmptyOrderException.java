package com.jk.cashregister.service.exception;

public class EmptyOrderException extends CustomException {
		public EmptyOrderException(String message) {
				super(message);
		}
		@Override
		public String getBundledKey() {
				return "order_empty";
		}
}
