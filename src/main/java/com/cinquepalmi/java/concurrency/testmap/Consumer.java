package com.cinquepalmi.java.concurrency.testmap;

import java.util.Map;
import java.util.Queue;

public class Consumer implements Runnable {
	private Drop drop;

	public Consumer(Drop drop) {
		this.drop = drop;
	}

	public void run() {
		Map<String, Queue<String>> map = drop.take();
		
		map.entrySet().stream().forEach(x ->{
			Queue<String> q = x.getValue();
			
			while(!q.isEmpty()) {
				String s = q.poll();
				System.out.println("MESSAGE RECEIVED:" + x.getKey() + " " + s);	
			}	
			
		});
		
	}
}
