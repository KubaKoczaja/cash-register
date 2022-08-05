package com.jk.cashregister.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "auth_user_group")
@Data@NoArgsConstructor
@AllArgsConstructor
public class AuthGroup {
		@Id
		@Column(name = "auth_user_group_id")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		@Column(name = "username")
		private String username;
		@Column(name = "auth_group")
		private String authGroup;

}
