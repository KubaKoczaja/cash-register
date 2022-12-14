package com.jk.cashregister.domain;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@AllArgsConstructor
public class CashRegisterUserPrincipal implements UserDetails {
		private User user;
		private List<AuthGroup> authGroups;

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
				if (authGroups == null) {
						return Collections.emptySet();
				}
				Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
				authGroups.forEach(group -> {
						grantedAuthorities.add(new SimpleGrantedAuthority(group.getAuthGroup()));
				});
				return grantedAuthorities;
		}

		@Override
		public String getPassword() {
				return this.user.getPassword();
		}

		@Override
		public String getUsername() {
				return this.user.getUsername();
		}

		@Override
		public boolean isAccountNonExpired() {
				return true;
		}

		@Override
		public boolean isAccountNonLocked() {
				return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
				return true;
		}

		@Override
		public boolean isEnabled() {
				return true;
		}
}
