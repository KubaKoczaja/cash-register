package com.jk.cashregister.service.exception;

public class IncorrectPasswordConfirmationException extends CustomException {
		public IncorrectPasswordConfirmationException(String message) {
				super(message);
		}

		@Override
		public String getBundledKey() {
				return "incorrect_password_confirmation";
		}
}
