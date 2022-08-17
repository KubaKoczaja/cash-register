package com.jk.cashregister.service;

import com.jk.cashregister.domain.User;
import com.jk.cashregister.repository.UserRepository;
import com.jk.cashregister.service.exception.UserDoesntExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

		@InjectMocks
		private UserService userService;
		@Mock
		private	UserRepository userRepository;
		private User userTest;

		@BeforeEach
		void setUp() {
				userTest = new User(1L, "testName","testLastName", "CASHIER", "testUserName","test", new ArrayList<>(), new ArrayList<>());
		}

		@Test
		void shouldReturnUserById() {
				when(userRepository.findById(anyLong())).thenReturn(Optional.of(userTest));
				User result = userService.getUserById(1L);
				assertEquals(userTest.getUsername(), result.getUsername());
		}

		@Test
		void shouldThrowExceptionWhenInvalidId() {
				when(userRepository.findById(anyLong())).thenThrow(UserDoesntExistException.class);
				assertThrows(UserDoesntExistException.class, () -> userService.getUserById(1L));
		}
}