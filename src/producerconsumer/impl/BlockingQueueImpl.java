package producerconsumer.impl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import producerconsumer.AbstractJobProcessor;
import producerconsumer.Job;

public class BlockingQueueImpl extends AbstractJobProcessor{
	private static final int CAPACITY = 10;
	
	private BlockingQueue<Job> queue = new ArrayBlockingQueue<Job>(CAPACITY);
	
	@Override
	public void produceJob(Job job) {
		try {
			queue.put(job);
			incrementProducedValue(job);
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
			incrementConsumedValue(job);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return job;
	}

}
