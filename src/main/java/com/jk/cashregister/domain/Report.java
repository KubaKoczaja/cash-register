package com.jk.cashregister.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "report")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Report {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@Column(name = "date_from")
		private LocalDateTime fromDate;

		@Column(name = "date_to")
		private LocalDateTime toDate;

		@Column(name = "content")
		private String content;

		@Column(name = "report_type")
		private String reportType;

		@ManyToOne
		@JoinColumn(name = "user_id", referencedColumnName = "id")
		private User user;
}
