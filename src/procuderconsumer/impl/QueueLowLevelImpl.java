package procuderconsumer.impl;

import java.util.ArrayList;
import java.util.List;

import procuderconsumer.AbstractJobProcessor;
import procuderconsumer.Job;

public class QueueLowLevelImpl extends AbstractJobProcessor {
	private static final int CAPACITY = 10;
	
	private List<Job> queue = new ArrayList<Job>();
	
	@Override
	public void produceJob(Job job) {
		synchronized(queue) {
			if (queue.size() >= CAPACITY) {
				try {
					while (queue.size() > CAPACITY) {
						queue.wait();
					}
					queue.add(job);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				queue.add(job);
				queue.notifyAll();
			}
		}
	}

	@Override
	public Job consumeJob() {
		Job job = null;
		synchronized (queue) {
			if (!queue.isEmpty()) {
				job = removeFromQueue();
				queue.notifyAll();
			} else {
				try {
					while (queue.isEmpty()) {
						queue.wait();
					}
					job = removeFromQueue();
					queue.notifyAll();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return job;
	}

	private Job removeFromQueue() {
		int lastIn = 0;
		Job job = queue.get(lastIn);
		queue.remove(lastIn);
		return job;
	}

}
