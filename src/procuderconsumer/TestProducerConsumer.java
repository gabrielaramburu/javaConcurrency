package procuderconsumer;

import java.util.concurrent.atomic.AtomicInteger;

import procuderconsumer.impl.BlockingQueueImpl;

public class TestProducerConsumer {
	
	private JobsProcessor jobsProcessor;
	private long startTime;
	private AtomicInteger jobCounter = new AtomicInteger(0);
	private int c = 0;
	
	public TestProducerConsumer() {
		this.jobsProcessor = new BlockingQueueImpl();
	}
	
	public void start() throws InterruptedException {
		initTimer();
		
		startProducers();
		//startConsumer();
		
		showFooter();
	}


	private void startProducers() {
		Thread producer1 = new Thread(new ProducerThread());
		Thread producer2 = new Thread(new ProducerThread());
		Thread producer3 = new Thread(new ProducerThread());
		
		producer1.start();
		producer2.start();
		producer3.start();
		
//		Thread producerControler = new Thread(new ControlerThread());
//		producerControler.start();
	}

	private void startConsumer() throws InterruptedException {
		Thread consumerThread = new Thread(new ConsumerThread());		
		consumerThread.start();
		
		consumerThread.join();
	}
	
	private void initTimer() {
		startTime = System.currentTimeMillis();
	}

	class ProducerThread implements Runnable {
		
		@Override
		public void run() {
			
			for (int i=0; i < Job.MAX_JOBS; i++) {
				//Job job = new Job(jobCounter.getAndIncrement(), random.nextInt(Job.MAX_JOB_VALUE));
				Job job = new Job(c++, 1);
				jobsProcessor.sendJob(job);
				
				System.out.println(Thread.currentThread().getName() + " Sending job:" + i);
			}
		}
		
	}

	class ConsumerThread implements Runnable {

		@Override
		public void run() {
			Job job;
			do {
				job = jobsProcessor.processJob();
				System.out.println("Processing job " + job.getId());
				
				pause(5);
			} while (!jobsProcessor.stopQueueProcessing(job));
			
		}
	}
	
	class ControlerThread implements Runnable {

		@Override
		public void run() {
			Job finalJob = new Job(true);
			jobsProcessor.sendJob(finalJob);
		}
		
	}
	
	
	private void pause(int milis) {
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void showFooter() {
		System.out.println(
				"Excecution result: producer=" + jobsProcessor.obtainTotalSended() + 
				", consumer=" + jobsProcessor.obtainTotalProcessed());
		
		System.out.println("Elapsed time: " + elapsedExecutionTime());
	}

	private long elapsedExecutionTime() {
		return System.currentTimeMillis() - startTime;
	}
}



