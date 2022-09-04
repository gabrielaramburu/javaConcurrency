package handoff;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueHandOff {

	public static void main(String[] args) {
		new Test().runTest();
	}
}

class Test {
	private BlockingQueue<Integer> queue = new SynchronousQueue<Integer>();

	private Integer getRandomValue() {
		return new Random().nextInt(1000);
	}

	public void runTest() {
		Thread producer = new Thread(() -> {
			try {
				while (true) {
					int value = getRandomValue();
					System.out.println("Produced:" + value);
					queue.put(value);
					
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		});

		Thread consumer = new Thread(() -> {
			try {
				while (true) {
					Thread.sleep(10);
					System.out.println("Consumed:" + queue.take());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		producer.setDaemon(true);
		consumer.setDaemon(true);
		producer.start();
		consumer.start();
		
		try {
			Thread.sleep(1000);
			System.out.println("End");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
