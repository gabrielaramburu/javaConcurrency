package producerconsumernewexamples;

import java.util.ArrayList;
import java.util.List;

public class ProducerConsumerWaitNotifyExample {
	static final int MAX_CAPACITY = 5;
	static List<Integer> queue = new ArrayList<Integer>();

	public static void main(String[] args) throws InterruptedException {
	
		Thread p1 = new Thread(createProducer(), "p1");
		Thread p2 = new Thread(createProducer(), "p2");
				
		Thread c1 = new Thread(createConsumer(), "p3");

		p1.start();
		p2.start();
		c1.start();

	}

	static Runnable createProducer() {
		return () -> {
			for (int i = 0; i < 10; i++) {
				synchronized (queue) {
					if (queue.size() > MAX_CAPACITY) {
						producerWait();

					} else {
						pause(10);
						System.out.println(Thread.currentThread().getName() + " Producing value: " + i);
						queue.add(i);
						queue.notifyAll();
					}
				}
			}
		};
	}

	static void producerWait() {
		try {
			while (queue.size() > MAX_CAPACITY) {
				System.out.println(Thread.currentThread().getName() + " Waiting for queue space.");
				queue.wait();
			}
		} catch (InterruptedException e) {
			System.err.println(e);
		}
	}
	
	static Runnable createConsumer() {
		return () -> {
			while (true) {
				synchronized (queue) {
					if (queue.size() <= 0) {
						consumerWait();

					} else {
						Integer value = queue.get(0);
						queue.remove(0);
						pause(1000);
						System.out.println("Consuming element: " + value);
						queue.notifyAll();
					}
				}
			}
		};
	}
	
	static void consumerWait() {
		try {
			System.out.println("Waiting for new values");
			while (queue.size() <= 0) {
				queue.wait();
			}

		} catch (InterruptedException e) {
			System.err.println(e);
		}
	}

	static void pause(int v) {
		try {
			Thread.sleep(v);
		} catch (InterruptedException e) {
			System.err.println(e);
		}
	}
}
