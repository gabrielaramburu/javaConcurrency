package procuderconsumer.impl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import procuderconsumer.Job;
import procuderconsumer.JobsProcessor;

public class BlockingQueueImpl implements JobsProcessor{
	private static final int CAPACITY = 10;
	
	private BlockingQueue<Job> queue = new ArrayBlockingQueue<Job>(CAPACITY);
	private int totalSended = 0;
	private int totalProcessed = 0;
	
	private Object lock = new Object();
	
	@Override
	public void produceJob(Job job) {
		try {
			queue.put(job);
			incrementTotal(job);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void incrementTotal(Job job) {
		synchronized (lock) {
			pause(5);
			totalSended = totalSended + job.getValue();
		}
	}


	private void pause(int i) {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Job consumeJob() {
		Job job = null;
		try {
			/* If the queue is empty the take method will wait until a new element arrives.
			 * Therefore there is no danger that job instance become null */
			job = queue.take();
			incrementProcessedJob(job);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return job;
		
	}

	private void incrementProcessedJob(Job job) {
		/* In order to better favor race conditions the pause must be closer to the sentence that change the shared resource
		 * (at least my testing cases)*/
		
		synchronized (lock) {
			pause(5);
			totalProcessed = totalProcessed + job.getValue();
		}
		
	}

	@Override
	public int obtainTotalProduced() {
		return totalSended;
	}

	@Override
	public int obtainTotalConsumed() {
		return totalProcessed;
	}

	@Override
	public boolean stopQueueProcessing(Job job) {
		if  (job.isFinalJob()) {
			System.out.println("Last job has been processed.");
			return true;
		} else return false;
	}

}
