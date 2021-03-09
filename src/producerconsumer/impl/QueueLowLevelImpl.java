package producerconsumer.impl;

import java.util.ArrayList;
import java.util.List;

import producerconsumer.AbstractJobProcessor;
import producerconsumer.Job;

public class QueueLowLevelImpl extends AbstractJobProcessor {
	private int maxCapacity;
	
	public QueueLowLevelImpl(int capacity) {
		super();
		this.maxCapacity = capacity;
	}

	private List<Job> queue = new ArrayList<Job>();
	
	@Override
	public void produceJob(Job job) {
		synchronized(queue) {
			if (queue.size() >= maxCapacity) {
				try {
					while (queue.size() >= maxCapacity) {
						queue.wait();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} 
			addJobToQueue(job);
			queue.notifyAll();
		}
	}
	
	private void addJobToQueue(Job job) {
		incrementProducedValue(job);
		queue.add(job);
	}

	@Override
	public Job consumeJob() {
		Job job = null;
		synchronized (queue) {
			if (queue.isEmpty()) {
				try {
					while (queue.isEmpty()) {
						queue.wait();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}		
			}
			
			job = removeFromQueue();
			queue.notifyAll();
		}
		return job;
	}

	private Job removeFromQueue() {
		int lastIn = 0;
		Job job = queue.get(lastIn);
		queue.remove(lastIn);
		incrementConsumedValue(job);
		return job;
	}

}
