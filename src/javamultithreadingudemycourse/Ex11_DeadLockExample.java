package javamultithreadingudemycourse;

public class Ex11_DeadLockExample {

	public static void main(String[] args) {
		Intersection inter = new Intersection();

		Runnable train1 = () -> {
			while(true) {
				inter.takeRailA();
				
			}
		};

		Runnable train2 = () -> {
			while(true) {
				inter.takeRailB();
				
			}
		};

		Thread t1 = new Thread(train1);
		Thread t2 = new Thread(train2);

		t1.start();
		t2.start();
	}

	
}



class Intersection {
	Object railA;
	Object railB;

	public Intersection() {
		this.railA = new Object();
		this.railB = new Object();
	}

	void takeRailA() {
		synchronized(railA) {		//the key to avoid deadlock is to adquired the block y the same order
			System.out.println(Thread.currentThread().getName() + " The rail A has been taken.");
			synchronized(railB) {	//same order
				System.out.println(Thread.currentThread().getName() + " The rail B has been taken.");
				System.out.println("The trainA is passing on rail A.");
				pause(500);
				System.out.println(System.currentTimeMillis() + " The trainA has passed");
				

			}
		}
	}

	void takeRailB() {
		synchronized(railA) { //same order respect to line 44 and 46
			System.out.println(Thread.currentThread().getName() + " The rail B has been taken.");
			synchronized(railB) { //same order respect to line 44 ans 46
				System.out.println(Thread.currentThread().getName() + " The rail A has been taken.");
				System.out.println("The trainB is passing on rail B.");
				pause(500);
				System.out.println(System.currentTimeMillis() + " The trainB has passed");
				
			}
		}
	}

	 void pause(long pause) {
		try {
			Thread.sleep(pause);
		} catch (InterruptedException e) {
			System.err.println("error");
		}

	}
}