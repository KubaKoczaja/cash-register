package com.jk.cashregister.service.exception;

public class UserAlreadyExistsException extends CustomException {
		public UserAlreadyExistsException(String message) {
				super(message);
		}

		@Override
		public String getBundledKey() {
				return "user_exists";
		}
}
