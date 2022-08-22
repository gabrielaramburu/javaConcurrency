package semaphore;

import java.util.concurrent.Semaphore;

public class SemaphoreExample {
	public static Semaphore s = new Semaphore(3);

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new Tester());
		Thread t2 = new Thread(new Tester());
		Thread t3 = new Thread(new Tester());
		Thread t4 = new Thread(new Tester());
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		
		t1.join();
		t2.join();
		t3.join();
		t4.join();
		System.out.println("end");
	}

}

class Tester implements Runnable {
	
	@Override
	public void run() {
		try {
			SemaphoreExample.s.acquire();
			System.out.println("Executing method: " + Thread.currentThread().getName());
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			SemaphoreExample.s.release();
		}
		
	}
	
}
