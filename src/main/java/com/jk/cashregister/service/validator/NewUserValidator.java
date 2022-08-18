package com.jk.cashregister.service.validator;

import com.jk.cashregister.service.dto.UserDTO;
import com.jk.cashregister.repository.UserRepository;
import com.jk.cashregister.service.exception.IncorrectPasswordConfirmationException;
import com.jk.cashregister.service.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewUserValidator {
		private final UserRepository userRepository;
		public void validate(UserDTO userDTO) {
				if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
						throw new IncorrectPasswordConfirmationException("Passwords aren't identical");
				}
				if(userRepository.existsByUsername(userDTO.getUsername())) {
						throw new UserAlreadyExistsException("User with such username already exists");
				}
		}
}