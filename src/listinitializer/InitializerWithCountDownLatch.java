package listinitializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class InitializerWithCountDownLatch {
	
	protected long startTime;
	protected List<Data> exampleDataList;
	protected int numberOfLists;
	protected int numberOfElements;
	protected int sumOfElements;
	protected CountDownLatch latch;
	
	
	public InitializerWithCountDownLatch(int numberOfLists, int numberOfElement) {
		this.exampleDataList = new ArrayList<Data>();
		this.latch = new CountDownLatch(numberOfLists);
		this.numberOfLists = numberOfLists;
		this.numberOfElements = numberOfElement;
	}
	
	public void startInitialization() {
		startTime = System.currentTimeMillis();
		
		for (int i=0; i < numberOfLists; i++) {
			new Thread(new InitializerThread()).start();
		}
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		showFooter();	
	}
	
	class InitializerThread implements Runnable{
		@Override
		public void run() {
			Data dataElement = new Data();
			dataElement.initializeData(numberOfElements);
			
			synchronized (exampleDataList) {
				exampleDataList.add(dataElement);
				sumarizesData(dataElement);
			}
		
			latch.countDown();
		}
	}
	
	private void sumarizesData(Data generetedData) {
		this.sumOfElements = this.sumOfElements + generetedData.getSumarizeData();
		
	}
	protected void showFooter() {
		System.out.println("=================================================");
		System.out.println("Number of list:" + exampleDataList.size());
		System.out.println("Sum of generated numbers:" + sumOfElements);
		System.out.println("Execution time: " + (System.currentTimeMillis() - startTime));
		
	}
	
	public List<Data> getList() {
		return Collections.unmodifiableList(exampleDataList);
	}

	public Integer getSumOfElements() {
		return sumOfElements;
	}
}

 
