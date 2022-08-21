package com.jk.cashregister.service;

import com.jk.cashregister.domain.AuthGroup;
import com.jk.cashregister.domain.User;
import com.jk.cashregister.service.dto.UserDTO;
import com.jk.cashregister.repository.AuthGroupRepository;
import com.jk.cashregister.repository.UserRepository;
import com.jk.cashregister.service.exception.UserDoesntExistException;
import com.jk.cashregister.service.mapper.AuthGroupMapper;
import com.jk.cashregister.service.mapper.UserDTOMapper;
import com.jk.cashregister.service.validator.NewUserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
		private final UserRepository userRepository;
		private final NewUserValidator newUserValidator;
		private final BCryptPasswordEncoder bCryptPasswordEncoder;
		private final AuthGroupRepository authGroupRepository;
		private final AuthGroupMapper authGroupMapper;


		public User getUserById(Long id) {
				return userRepository.findById(id).orElseThrow(() -> new UserDoesntExistException("Such user doesn't exist"));
		}

		public User getAuthenticatedUser() {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				return userRepository.findByUsername(authentication.getName());
		}
		@Transactional
		public void register(UserDTO userDTO) {
				newUserValidator.validate(userDTO);
				userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
				User user = UserDTOMapper.INSTANCE.userDTOToUser(userDTO);
				userRepository.save(user);
				AuthGroup authGroup = authGroupMapper.mapTpAuthGroup(userDTO);
				authGroupRepository.save(authGroup);
		}

}
