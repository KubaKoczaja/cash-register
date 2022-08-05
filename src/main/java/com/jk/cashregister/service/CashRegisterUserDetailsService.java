package com.jk.cashregister.service;

import com.jk.cashregister.domain.AuthGroup;
import com.jk.cashregister.domain.CashRegisterUserPrincipal;
import com.jk.cashregister.domain.User;
import com.jk.cashregister.repository.AuthGroupRepository;
import com.jk.cashregister.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CashRegisterUserDetailsService implements UserDetailsService {
		private final UserRepository userRepository;
		private final AuthGroupRepository authGroupRepository;

		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				User user = this.userRepository.findByUsername(username);
				if (user == null) {
						throw new UsernameNotFoundException("User can't be found: " + username);
				}
				List<AuthGroup> authGroups = authGroupRepository.findByUsername(username);
				return new CashRegisterUserPrincipal(user,authGroups);
		}
}
