package com.jk.cashregister.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "STOCK")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = "id")
public class Stock {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@Column(name = "product_code")
		private String productCode;

		@Column(name = "product_name")
		private String productName;

		@Column(name = "quantity")
		private int quantity;

		@Column(name = "price")
		private int price;

		@OneToMany(mappedBy = "stock")
		private List<OrderItem> orderItemList;

}
