package handoff;

import java.util.Random;

/**
 * @author gabriel
 * When sentence (1) execute before than sentendc (3) the program waits forever
 * When sentence (4) execute before than sentence (2) the program waits forever.
 * That the reason for the timeout in sentence (3). It also ok tu put it in line (2)
 */
public class LowLevelHandOff {
	private Information info;
	private Object lockProducer;
	private Object lockConsumer;

	public LowLevelHandOff() {
		this.info = new Information();
		this.lockConsumer = new Object();
		this.lockProducer = new Object();
	}

	public static void main(String[] args) {
		new LowLevelHandOff().test();
	}

	public void test() {
		Thread producer = new Thread(new Runnable() {
			public void run() {
				while (true) {
					
					synchronized (lockProducer) {
						int val = Information.newValue(1000);
						info.setValue(val);
						System.out.println(info.getCounter() + ", New generated value: " + val);
						synchronized(lockConsumer) {
							lockConsumer.notify(); //(1)
						}
						try {
							lockProducer.wait(); //(2)
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
					}
				
			}
		}});
		
		Thread consumer = new Thread(new Runnable() {
			public void run() {
				while (true) {
					synchronized(lockConsumer) {
						try {
							lockConsumer.wait(100); //(3)
							System.out.println("Consumed value: " + info.getValue());
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
	
					synchronized (lockProducer) {
						lockProducer.notify(); //(4)
					}
				}
			}
		});
		
		producer.start();
		consumer.start();
		
		
	}

}

class Information {
	private int value;
	private int counter = 0;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
		counter++;
	}

	public int getCounter() {
		return this.counter;
	}
	public static int newValue(int maxValue) {
		int val = new Random().nextInt(maxValue);
		return val;
	}

}
