package leetcode;

/* https://leetcode.com/problems/print-in-order/ */

public class PrintInOrder {
	Object lock1 = new Object();
	Object lock2 = new Object();

	public PrintInOrder() {

	}

	public void first(Runnable printFirst) throws InterruptedException {

		// printFirst.run() outputs "first". Do not change or remove this line.
		printFirst.run();
		Thread.sleep(50);// to prevent liveLock , is there a better way to do this?
		synchronized (lock1) {
			lock1.notify();
		}

	}

	public void second(Runnable printSecond) throws InterruptedException {
		synchronized (lock1) {
			lock1.wait();
			// printSecond.run() outputs "second". Do not change or remove this line.
			printSecond.run();
			synchronized (lock2) {
				lock2.notify();
			}
		}

	}

	public void third(Runnable printThird) throws InterruptedException {
		synchronized (lock2) {
			lock2.wait();
			// printThird.run() outputs "third". Do not change or remove this line.
			printThird.run();
		}
	}
}
