package com.jk.cashregister.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
		@NotBlank(message = "{firstName.notblank}")
		private String firstName;
		@NotBlank(message = "{lastName.notblank}")
		private String lastName;
		@NotBlank(message = "{position.notblank}")
		private String position;
		@NotBlank(message = "{username.notblank}")
		private String username;
		@Size(message = "{password.size}", min=4, max=10)
		private String password;

		private String confirmPassword;
}
