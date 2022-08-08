package com.jk.cashregister.util;

public class URLs {
		private URLs() {
				throw new IllegalStateException("Utility class");
		}
		public static final String STOCK_ROOT = "/stock";
		public static final String REPORT_ROOT = "/report";
		public static final String ORDER_ROOT = "/order";
		public static final String REDIRECT = "redirect:";
		public static final String ID_OPENORDER = "/{id}/openorder";
		public static final String ID_DETAILS = "/{id}/details";
}
