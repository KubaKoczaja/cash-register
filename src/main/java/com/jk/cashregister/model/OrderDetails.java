package com.jk.cashregister.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "order_details")
@AllArgsConstructor
@Data
public class OrderDetails {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@ManyToOne
		@JoinColumn(name = "stock_id", referencedColumnName = "id")
		private Stock stock;

		@Column(name = "quantity_ordered")
		private int quantityOrdered;

		@ManyToOne
		@JoinColumn(name = "order_id", referencedColumnName = "id")
		private Order order;
}
