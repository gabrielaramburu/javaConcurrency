package counter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RunnerWithExcecutorService {
	private static final int NUMBERS_OF_ITERATION = 100;
	
	private Countable countable;
	private int numberOfThread;
	private String testName;
	
	public RunnerWithExcecutorService(String testName, Countable countableImpl, int numberOfThread) {
		this.testName = testName;
		this.countable =  countableImpl;
		this.numberOfThread = numberOfThread;
	}
	
	
	public void startTest() throws InterruptedException {
		
		showHeaderInformation();
		
		
		ExecutorService excecutor = Executors.newFixedThreadPool(numberOfThread);
		for (int i = 0; i< this.numberOfThread; i++) {
			
			excecutor.execute(new ThreadCountable());
		}
		
		//Timing coupling in this API.
		excecutor.shutdown();
		excecutor.awaitTermination(2, TimeUnit.MINUTES);
		
		showFooterInformation();
	}
	

	private void showHeaderInformation() {
		System.out.println("**" + this.testName + " started.");
		System.out.println(this.countable.obtainDescription());
		
	}


	private String obtainFinalStatus() {
		if (obtainExpectedValue() == this.countable.obtainFinalValue()) return "OK";
		else return "ERROR";
	}
	
	private int obtainExpectedValue() {
		return NUMBERS_OF_ITERATION * this.numberOfThread;
	}
	
	private void showFooterInformation() {
		System.out.println(this.testName + " finished. Result:" +obtainFinalStatus() +", expected value:" + obtainExpectedValue() 
		+ ", actual value:" + this.countable.obtainFinalValue());
		System.out.println("===============================================");
	}

	
	class ThreadCountable implements Runnable {
		@Override

		public void run() {
			System.out.println("Inicio thread: " + Thread.currentThread().getName());
			for (int i = 0; i< NUMBERS_OF_ITERATION; i++) {
				countable.increment();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Fin thread: " + Thread.currentThread().getName());
		}
		
	}

	
}

