package com.practice.hackathon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

@Service
public class PagingAndSortingService {

	Function<Map<String,String>, Optional<List<Order>>> getSortOrder = (m) -> {
		List<Order> sortOrder = new ArrayList();
		m.entrySet().stream().forEach(
				e->{
					if(e.getValue().toLowerCase().startsWith("asc") || e.getValue().equals("1"))
						sortOrder.add(Order.asc(e.getKey()));
					else
						sortOrder.add(Order.desc(e.getKey()));
				});
		return Optional.ofNullable(sortOrder);};

	public Pageable getPageRequest(int page, int size, Optional<Map<String,String>> sort){
		PageRequest pageable = sort.flatMap(t->getSortOrder.apply(t)).flatMap(
				l->{
					PageRequest pageRequest = PageRequest.of(page, size, Sort.by(l)); 
					return Optional.of(pageRequest);
					}).orElseGet(()->PageRequest.of(page, size));
		/*if(pageable instanceof Pageable)
			return (PageRequest) pageable;
		else if(pageable instanceof Optional)
			return ((Optional<PageRequest>)pageable).get();
		else */
			return pageable;
	}
}
