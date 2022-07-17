package com.jk.cashregister.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "EMPLOYEE")
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class Employee {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@Column(name = "first_name")
		private String firstName;

		@Column(name = "last_name")
		private String lastName;

		@Column(name = "job_position")
		private String position;

		@OneToMany(mappedBy = "employee")
		private List<Order> orders;
}
