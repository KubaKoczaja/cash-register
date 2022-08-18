package com.jk.cashregister.service.mapper;

import com.jk.cashregister.domain.AuthGroup;
import com.jk.cashregister.service.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class AuthGroupMapper {
		public AuthGroup mapTpAuthGroup(UserDTO userDTO) {
				AuthGroup authGroup = new AuthGroup();
				authGroup.setUsername(userDTO.getUsername());
				authGroup.setAuthGroup(userDTO.getPosition());
				return authGroup;
		}
}
