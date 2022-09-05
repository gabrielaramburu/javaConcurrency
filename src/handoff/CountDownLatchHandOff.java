package handoff;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * The idea does not works within a loop
 * Maybe with two barrier?
 *
 */
public class CountDownLatchHandOff {

	public static void main(String[] args) throws InterruptedException {
		new Test2().run();
	}

}


class Test2 {
	private int value = 0;
	private CountDownLatch barrier = new CountDownLatch(1);

	public void run() throws InterruptedException {
		Thread producer = new Thread(() -> {
			//while (true) {
				value = getRandomValue();
				System.out.println("Produced:" + value);
				barrier.countDown();
			//}

		});

		Thread consumer = new Thread(() -> {
			//while (true) {
				try {
					barrier.await();
					System.out.println("Consumed:" + value);
				
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			//}

		});
		producer.setDaemon(true);
		consumer.setDaemon(true);
		producer.start();
		consumer.start();
		
		Thread.sleep(1000);
		System.out.println("End");

	}

	private Integer getRandomValue() {
		return new Random().nextInt(1000);
	}
}
