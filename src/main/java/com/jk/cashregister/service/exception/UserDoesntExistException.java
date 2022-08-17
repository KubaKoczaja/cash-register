package com.jk.cashregister.service.exception;

public class UserDoesntExistException extends CustomException {
		public UserDoesntExistException(String message) {
				super(message);
		}

		@Override
		public String getBundledKey() {
				return "user_does_not_exists";
		}
}
