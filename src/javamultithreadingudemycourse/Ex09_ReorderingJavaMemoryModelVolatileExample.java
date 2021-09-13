package javamultithreadingudemycourse;


public class Ex09_ReorderingJavaMemoryModelVolatileExample {
	static  int i = 0;
	static  int j = 0;
	static volatile boolean found = false;

	public static void main(String[] args) throws InterruptedException {

		Thread t1 = new Thread(() -> {
			int x = 0;
			while (!found) {
				one();
				x++;
				if ((x & (x - 1)) == 0)
					System.out.println(x);
			}

			;}
		);
		Thread t2 = new Thread(() -> {
			while (!found) {
				two();
			}
		});

		t1.start();
		t2.start();

		t1.join();
		t2.join(); 
		System.out.println("End");
	}

	static void one(){
		i++;
		j++;
	}

	static void two() {
		if (j > i) {
			System.out.println("j is greather than i ->" + j + " vs " + i);
			found = true;
		}
	}
}