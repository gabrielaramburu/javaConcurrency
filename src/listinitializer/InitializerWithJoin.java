package listinitializer;

import java.util.ArrayList;
import java.util.List;

public class InitializerWithJoin extends InitializerWithCountDownLatch{

	public InitializerWithJoin(int numberOfLists, int numberOfElement) {
		super(numberOfLists, numberOfElement);
		
	}

	@Override
	public void startInitialization()  {
		startTime = System.currentTimeMillis();
		
		List<Thread> listRunningThread = new ArrayList<Thread>();
		
		for (int i=0; i < numberOfLists; i++) {
			Thread thread = new Thread(new InitializerThread());
			thread.start();
			listRunningThread.add(thread);
		}

		for (Thread thread: listRunningThread) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		showFooter();	
	}
	
	

}
