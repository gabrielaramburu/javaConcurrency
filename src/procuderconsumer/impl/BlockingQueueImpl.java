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
	public void sendJob(Job job) {
		try {
			queue.put(job);
			incrementTotal(job);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void incrementTotal(Job job) {
//		synchronized (lock) {
//			totalSended = totalSended + job.getValue();
//		}
		totalSended = totalSended + job.getValue();
	}


	@Override
	public Job processJob() {
		Job job = null;
		try {
			/* is the queue is empty the take method will wait until a new element arrives.
			 * therefore there is no danger that job instance become null */
			job = queue.take();
			incrementProcessedJob(job);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return job;
		
	}

	private void incrementProcessedJob(Job job) {
//		synchronized (lock) {
//			totalProcessed = totalProcessed + job.getValue();
//		}
		
		totalProcessed = totalProcessed + job.getValue();
	}

	@Override
	public int obtainTotalSended() {
		return totalSended;
	}

	@Override
	public int obtainTotalProcessed() {
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
