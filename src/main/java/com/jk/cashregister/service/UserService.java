package com.jk.cashregister.service;

import com.jk.cashregister.domain.User;
import com.jk.cashregister.repository.UserRepository;
import com.jk.cashregister.service.exception.UserDoesntExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
		private final UserRepository userRepository;

		public User getUserById(Long id) {
				return userRepository.findById(id).orElseThrow(() -> new UserDoesntExistException("Such user doesn't exist"));
		}

		public User getAuthenticatedUser() {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				return userRepository.findByUsername(authentication.getName());
		}

}
