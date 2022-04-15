package com.ab.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Java8Test {
	
	public static void main(String[] args) {
		
		List<POJO> pojoList = new ArrayList<>();
		
		
		POJO pojo = new POJO(1, "A", "B", "C");
		POJO pojo1 = new POJO(12, "A", "B", "C");
		POJO pojo3 = new POJO(14, "A", "B", "C");
		POJO pojo2 = new POJO(134, "A", "B", "C");
		POJO pojo4 = new POJO(124, "A", "B", "C");
		POJO pojo5 = new POJO(1245, "A", "B", "C");
		POJO pojo6 = new POJO(1214, "A", "B", "C");
		
		pojoList.add(pojo6);
		pojoList.add(pojo5);
		pojoList.add(pojo4);
		pojoList.add(pojo3);
		pojoList.add(pojo2);
		pojoList.add(pojo1);
		pojoList.add(pojo);
		
		Optional<POJO> optPojo = pojoList.stream()
				.filter(p -> p.getId()==142)
				.findAny();
		
		if(optPojo.isPresent()) {
			System.out.println(optPojo.get());
		}else {
			System.out.println("Not Present");
		}
		
	}

}
