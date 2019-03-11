package com.practice.hackathon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ch.qos.logback.core.net.SyslogOutputStream;
public class Test {

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
		PageRequest x = sort.flatMap(t->getSortOrder.apply(t)).flatMap(
				l->{
					PageRequest pageRequest = PageRequest.of(page, size, Sort.by(l)); 
					return Optional.of(pageRequest);
					}).orElseGet(()->PageRequest.of(page, size));
		/*if(x instanceof Pageable)
			return (PageRequest) x;
		else
			return ((Optional<PageRequest>)x).get();*/
		return x;
	}
	
	public static void main(String[] args) {
		
		BCryptPasswordEncoder e = new BCryptPasswordEncoder();
		String res = e.encode("p@ssWord123");
		System.out.println(res);
		Map<String,String> map = new HashMap();
		Test t = new Test();
		map.put("name","ascendin");
		map.put("title","descendin");
		map.put("dob","ascendin");
		map.put("desi","ascendin");
		
		Optional<Map<String,String>> opMap = Optional.of(map);
		/*opMap.flatMap((n) -> {return Optional.of(Sort.by(""));});*/
		
		Pageable p =t.getPageRequest(0, 1,opMap);
		System.out.println(p.getPageNumber());
		p.getSort().get().forEach(s->System.out.println(s.getProperty()));
	}

}
