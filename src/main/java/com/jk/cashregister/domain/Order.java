package com.jk.cashregister.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "purchase_order")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@Column(name = "open_date")
		private LocalDateTime openDate;

		@Column(name = "close_date")
		private LocalDateTime closeDate;

		@ManyToOne
		@JoinColumn(name = "user_id", referencedColumnName = "id")
		private User user;

		@OneToMany(mappedBy = "order")
		private List<OrderItem> orderItemList;


}
