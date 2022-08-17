package com.jk.cashregister.service.exception;

public abstract class CustomException extends RuntimeException{
		protected CustomException(String message) {
				super(message);
		}
		public abstract String getBundledKey();
}
