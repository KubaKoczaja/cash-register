package com.jk.cashregister.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PagingTest {
		private Paging<Object> paging;
		private List<Object> list;

		@BeforeEach
		void setUp() {
				paging = new Paging<>();
				list = new ArrayList<>();

				list.add(new Object());
				list.add(new Object());
				list.add(new Object());

		}

		@Test
		void shouldReturnPagedListWithTwoElementsOnEveyPage() {
				Page<Object> result = paging.getPage(1,list, 2);
				assertEquals(2, result.getTotalPages());
				assertEquals(3, result.getTotalElements());
		}
}