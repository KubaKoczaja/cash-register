package com.jk.cashregister.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
		@GetMapping(value = {"/","/index","/home"})
		public String getIndexPage() {
				return "/index";
		}

		@GetMapping("/login")
		public String getLoginPage(Model model) {
				return "/login";
		}

		@GetMapping("/logout-success")
		public String getLogoutPage(Model model) {
				return "/logout";
		}
}
