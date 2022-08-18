package com.jk.cashregister.controller;

import com.jk.cashregister.service.dto.UserDTO;
import com.jk.cashregister.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static com.jk.cashregister.util.URLs.REDIRECT;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class UserRegisterController {
		private final UserService userService;
		@GetMapping
		public String getRegisterView(Model model) {
				model.addAttribute("userDTO", new UserDTO());
				return "/register";
		}

		@PostMapping
		public String registerUserAccount(@Valid UserDTO userDTO) {
				userService.register(userDTO);
				return REDIRECT + "/register?success";
		}
}