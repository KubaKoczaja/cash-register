package com.jk.cashregister.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cr_user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = "id")
public class User {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@Column(name = "first_name")
		private String firstName;

		@Column(name = "last_name")
		private String lastName;

		@Column(name = "job_position")
		private String position;

		@Column(name = "username")
		private String username;

		@Column(name = "user_password")
		private String password;

		@OneToMany(mappedBy = "user")
		private List<Report> reports;

		@OneToMany(mappedBy = "user")
		private List<Order> orders;
}
