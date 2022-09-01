package executorservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ExecutorExamples {

	public static void main(String[] args) {
		example1();
		example2();
		System.out.println("End");
	}

	private static void example1() {
		Executor exec = Executors.newSingleThreadExecutor();
		exec.execute(()-> System.out.println("Hello world"));
	}
	
	private static void example2() {
		Runnable run = new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		};
		//corePoolSize and maxPoolSize are setted to 2
		ExecutorService exec = Executors.newFixedThreadPool(2);
		exec.submit(run);
		exec.submit(run);
		exec.submit(run);
		exec.submit(run);
			
		assertEquals(2, ((ThreadPoolExecutor)exec).getPoolSize());
		assertEquals(2, ((ThreadPoolExecutor)exec).getQueue().size());

	}
}
