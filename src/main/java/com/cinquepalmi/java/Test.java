package com.cinquepalmi.java;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Test {

	int i;
	
	public static class Person{
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Person(String name) {
			super();
			this.name = name;
		}
		
	}
	
	public void printName(Person p) {
		p = new Person("ZIO PINUCCIo");
//		p.setName("Zio Pino");
		System.out.println(p.getName());
	}
	
	public void printAge(Double age) {
		age = 2D;
		System.out.println(age);
	}
	
	public Test() {
		System.out.println("costruttore" + i ++);
	}
	
	public static void main(String[] args) {
//		Test t = new Test();
//		
//		Map<String, String > map = new HashMap<String, String>();
//		map.put("key", "value");
//		map.put("key1", "value1");
//		map.put("key2", "value2");
//		map.put("key3", "value3");
//		
//		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
//		while(iterator.hasNext()) {
//			Entry<String, String> entry = iterator.next();
//			System.out.println(entry.getKey());
//			iterator.remove();
//		}
//		
//		map.put("key test", "value");
//		iterator = map.entrySet().iterator();
//		System.out.println(iterator.hasNext());
//		while(iterator.hasNext()) {
//			Entry<String, String> entry = iterator.next();
//			System.out.println(entry.getKey());
//		}
		
		Map<String, String> map = new HashMap<>();
		map.put("11", "2");
		map.put("8", "1");
		map.put("30", "3");
		
//		Set<String> str = new HashSet(map.keySet());
//		Set<String> str2 = map.keySet();
//		System.out.println("Keyset size " + str.size());
//		System.out.println("Keyset2 size " + str2.size());
//		
//		Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
//		while(it.hasNext()) {
//			System.out.println(it.next().getValue());
//			it.remove();
//		}
//		
//		System.out.println("Keyset size " + str.size());
//		System.out.println("Keyset2 size " + str2.size());
		Comparator<Entry<String, String>> comparable = new Comparator<Entry<String, String>>() {

			@Override
			public int compare(Entry<String, String> o1, Entry<String, String> o2) {
				return Long.valueOf(o1.getKey()).compareTo(Long.valueOf(o2.getKey()));
			}

		};
		
		map.entrySet().stream().sorted(comparable).forEach( 
				xx -> System.out.println(xx.getValue())
		);
		
		Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
		while(it.hasNext()) {
			System.out.println(it.next().getValue());
			it.remove();
		}
		
		
		Person p = new Person("Michele Nanna");
		Test t = new Test();
		t.printName(p);
		System.out.println(p.getName() + " Ma che cazzo");
		
		System.out.println("XXXXXXXXXXXXXXXXXX");
		Double cd = 1D;
		t.printAge(cd);
		System.out.println(cd);
		
		
		
		System.out.println(new String("Zio.figa").split("\\.")[1]);
	}
	
	
	
}
