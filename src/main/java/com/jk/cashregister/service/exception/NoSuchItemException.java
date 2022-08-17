package com.jk.cashregister.service.exception;

public class NoSuchItemException extends CustomException{
		public NoSuchItemException(String message) {
				super(message);
		}

		@Override
		public String getBundledKey() {
				return "item_does_not_exists";
		}
}
