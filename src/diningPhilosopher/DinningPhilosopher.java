package diningPhilosopher;

import java.util.*;

public class DinningPhilosopher {

	public static void main(String[] args) throws InterruptedException {
		Stick one = new Stick(1);
		Stick two = new Stick(2);
		Stick three = new Stick(3);
		Stick four = new Stick(4);
		Stick five = new Stick(5);

		Thread p1 = new Thread(new Philosofer(one, two), "P1");
		Thread p2 = new Thread(new Philosofer(two, three), "P2");
		Thread p3 = new Thread(new Philosofer(three, four), "P3");
		Thread p4 = new Thread(new Philosofer(four, five), "P4");
		Thread p5 = new Thread(new Philosofer(five, one), "P5");

		p1.start();
		p2.start();
		p3.start();
		p4.start();
		p5.start();
		
		p1.join();
		p2.join();
		p3.join();
		p4.join();
		p5.join();
	}

}

class Philosofer implements Runnable {
	Stick left;
	Stick right;
	int eatingCount = 0;

	Philosofer(Stick left, Stick right) {
		//TODO fix bug assigning stick y global same order 
		//(I know how to implement it but I not sure what that means, I have to think about befero to implement)
		this.left = left;
		this.right = right;
	}

	@Override
	public void run() {
		while(true) {
			status("Waiting");
			randomPause(10);
			//this code produce DeadLock when the five philosopher take the left stick simultaneously
			synchronized (left) {
				synchronized (right) {
					status("Eating");
					randomPause(10);
					eatingCount++;
				}
			}
		}
	}

	private void status(String status) {
		String t = Thread.currentThread().getName();
		System.out.println(this.eatingCount + ": " + t + " " + status);
	}
	private void randomPause(int limit) {
		try {
			Random ran = new Random();
			Thread.sleep(ran.nextInt(limit + 1));
		} catch (InterruptedException e) {
			System.err.println("Error in pause");
		}

	}
}

class Stick {
	int val;

	Stick(int val) {
		this.val = val;
	}
}
