package com.jk.cashregister.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "purchase_order")
@AllArgsConstructor
@Data
public class Order {
		@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@Column(name = "date_of_creation")
		private LocalDateTime dateOfCreation;

		@ManyToOne
		@JoinColumn(name = "employee_id", referencedColumnName = "id")
		private Employee employee;

		@OneToMany(mappedBy = "order")
		private List<OrderDetails> orderDetailsList;


}
