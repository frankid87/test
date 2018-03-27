package com.cinquepalmi.java.concurrency.testmap;

import java.util.Random;

public class Producer implements Runnable {
	private Drop drop;

	public Producer(Drop drop) {
		this.drop = drop;
	}

	public void run() {
		String[] importantInfo = {
				"AA","BB","CC",
				"AAA","BBB","CCC",
				"AAAA","BBBB","CCCC",
				"AAAAA","BBBBB","CCCCC",
		};
		
		Random random = new Random();

		for (int i = 0;	i < importantInfo.length; i++) {
			System.out.println("PRODUCER " + importantInfo[i]);
			
			drop.put(importantInfo[i]);
			try {
				Thread.sleep(random.nextInt(5000));
			} catch (InterruptedException e) {}
		}
	}

}