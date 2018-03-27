package com.cinquepalmi.java.concurrency.testmap;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Drop {

	private Queue<String> queue = new LinkedList<>();
	
	private boolean isEmpty() {
		return queue.size() < 5;
	}
	
	public synchronized Map<String, Queue<String>> take() {
		
		// Wait until message is available.
		while (this.isEmpty()) {
			try {
				System.out.println("DROP TAKE WAIT");
				wait();
			} catch (InterruptedException e) {}
		}
		
		System.out.println("DROP TAKE");
		
		Map<String, Queue<String>> map = new HashMap<String, Queue<String>>();
		
		while(!queue.isEmpty()) {
			String str = queue.poll();
			String index = str.substring(1, 2);

			Queue<String> q = map.get(index);
			if(q == null)
				q = new LinkedList<String>();
			q.add(str);
			map.put(index, q);
		}						
		return map;
	}

	public synchronized void put(String string) {
		// Wait until message has
		// been retrieved.
		while (!this.isEmpty()) {
			try { 
				System.out.println("DROP PUT WAIT");
				wait();
			} catch (InterruptedException e) {}
		}
		
		System.out.println("DROP PUT " + string);
		
		// Store message.
		this.queue.add(string);
		
		if(!this.isEmpty()) {
			// Notify consumer that status
			// has changed.
			notifyAll();
		}
	}
	
	public static void main(String[] args) {
		Drop drop = new Drop();
		(new Thread(new Consumer(drop))).start();
		(new Thread(new Producer(drop))).start();
	}
	
}