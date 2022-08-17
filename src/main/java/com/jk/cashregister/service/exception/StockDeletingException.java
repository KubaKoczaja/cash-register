package com.jk.cashregister.service.exception;

public class StockDeletingException extends CustomException {
		public StockDeletingException(String message) {
				super(message);
		}

		@Override
		public String getBundledKey() {
				return "stock_active_delete";
		}
}
