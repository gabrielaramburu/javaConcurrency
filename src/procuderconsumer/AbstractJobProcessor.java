package procuderconsumer;

public abstract class AbstractJobProcessor implements JobsProcessor{
	private int totalProduced = 0;
	private int totalConsumed = 0;
	
	private Object lock = new Object();
	
	@Override
	public int obtainTotalProduced() {
		return totalProduced;
	}

	@Override
	public int obtainTotalConsumed() {
		return totalConsumed;
	}
	
	@Override
	public boolean stopQueueProcessing(Job job) {
		if  (job.isFinalJob()) {
			System.out.println("Last job has been processed.");
			return true;
		} else return false;
	}
	
	protected void incrementProducedValue(Job job) {
		synchronized (lock) {
			pause(5);
			totalProduced = totalProduced + job.getValue();
		}
	}
	
	protected void incrementConsumedValue(Job job) {
		/* In order to better favor race conditions the pause must be closer to the sentence that change the shared resource
		 * (at least my testing cases)*/
		
		synchronized (lock) {
			pause(5);
			totalConsumed = totalConsumed + job.getValue();
		}
		
	}
	
	private void pause(int i) {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
}
