package com.jk.cashregister.domain.dto;

import com.jk.cashregister.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReportDTO {

		@NotBlank
		private String reportType;

		private User user;
}
