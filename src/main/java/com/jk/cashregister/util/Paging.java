package com.jk.cashregister.util;

import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NoArgsConstructor
public class Paging<T> {
		public Page<T> getPage(int page, List<T> list, int size) {
				PageRequest request = PageRequest.of(page - 1, size);
				int start = (int) request.getOffset();
				int end = Math.min((start + request.getPageSize()), list.size());
				int totalRows = list.size();
				return new PageImpl<>(list.subList(start, end), request, totalRows);
		}
}
