package com.jk.cashregister.controller;

import com.jk.cashregister.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {
		private final UserService userService;
		@GetMapping(value = {"/","/index","/home"})
		public String getIndexPage(Model model) {
				model.addAttribute("loggedUser", userService.getAuthenticatedUser());
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
