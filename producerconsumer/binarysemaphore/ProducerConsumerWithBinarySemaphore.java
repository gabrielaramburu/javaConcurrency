package binarysemaphore;

import java.util.*;
import java.util.concurrent.*;

public class ProducerConsumerWithBinarySemaphore {
	static Integer sharedResource = null;
	static Semaphore full = new Semaphore(0);
	static Semaphore empty = new Semaphore(1);
 
	public static void main(String[] args) {
		Thread consumer = new Thread(() -> {
			while (true) {
				try {
					full.acquire();	
				} catch (InterruptedException e){};
				pause(1000);
				System.out.println("C -> " + sharedResource);
				sharedResource = null;
				empty.release();
			}
		});

		Thread producer = new Thread(() -> {
			Random ran = new Random();
			while (true) {
				try {
					empty.acquire();	
				} catch (InterruptedException e) {};
				pause(2000);
				sharedResource = ran.nextInt();
				System.out.println("P -> " + sharedResource);
				
				full.release();
			}
		});

		consumer.start();
		producer.start();
	}

	private static void pause(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e){
			System.err.println("error");
		}
	}

}
