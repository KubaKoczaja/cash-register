package com.jk.cashregister.service.validator;

import com.jk.cashregister.repository.UserRepository;
import com.jk.cashregister.service.dto.UserDTO;
import com.jk.cashregister.service.exception.IncorrectPasswordConfirmationException;
import com.jk.cashregister.service.exception.UserAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewUserValidatorTest {
		@InjectMocks
		private NewUserValidator validator;
		@Mock
		private UserRepository userRepository;
		private UserDTO userDTOInput;

		@BeforeEach
		void setUp() {
				userDTOInput = new UserDTO("name", "lastname", "CASHIER", "username", "password", "password");
		}

		@Test
		void shouldThrowExceptionInformingOfInvalidPasswordWhenPasswordsDoesNotMatch() {
				userDTOInput.setConfirmPassword("password1");
				assertThrows(IncorrectPasswordConfirmationException.class, () -> validator.validate(userDTOInput));
		}
		@Test
		void shouldThrowExceptionInformingUserAlreadyExistsWhenUserWithSuchUsernameCanBeFound() {
				when(userRepository.existsByUsername(anyString())).thenReturn(true);
				assertThrows(UserAlreadyExistsException.class, () -> validator.validate(userDTOInput));
		}
}