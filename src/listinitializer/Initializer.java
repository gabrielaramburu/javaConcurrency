package listinitializer;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Initializer {
	
	private long startTime; 
	
	private List<Data> exampleDataList = new ArrayList<Data>();
	private int numberOfLists;
	private int numberOfElements;
	
	
	public Initializer(int numberOfLists, int numberOfElement) {
		this.numberOfLists = numberOfLists;
		this.numberOfElements = numberOfElement;
	}
	
	public void startInitialization() throws InterruptedException {
		startTime = System.currentTimeMillis();
		
		Thread thread = null;
		for (int i=0; i < numberOfLists; i++) {
			thread = new Thread(new InitialiterThread());
			thread.start();
		}

		//I think this generate a bug because its assumes that the last instantiated thread will be the last one to finish.
		//thread.join();
		
		synchronized (thread) {
			thread.wait();
			showFooter();
		}
		
		
	}
	
	class InitialiterThread implements Runnable{
		@Override
		public void run() {
			Data dataElement = new Data();
			dataElement.initializeData(numberOfElements);
			
			synchronized (exampleDataList) {
				exampleDataList.add(dataElement);
			}
			
		}
	}
	
	private void showFooter() {
		System.out.println("=================================================");
		System.out.println("Number of list:" + exampleDataList.size());
		System.out.println("Execution time: " + (System.currentTimeMillis() - startTime));
	}
	
}

 
