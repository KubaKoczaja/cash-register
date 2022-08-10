package com.jk.cashregister.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReportDTO {

		@NotBlank(message = "{reportType.notblank}")
		private String reportType;
}
