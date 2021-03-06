package procuderconsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerConsumer {
	
	private int numbersOfJobs;
	private JobsProcessor jobsProcessor;
	private Checker checker;
	private CountDownLatch latch = new CountDownLatch(4);
	private List<Thread> producerThreads = new ArrayList<Thread>();
	
	private long startTime;
	private AtomicInteger jobCounter = new AtomicInteger(0);
	
	public ProducerConsumer(JobsProcessor jobProcessor, int jobs) {
		this.jobsProcessor = jobProcessor;
		this.numbersOfJobs = jobs;
		this.checker = new Checker();
	}
	
	public void start() throws InterruptedException {
		initTimer();
		
		startProducers();
		startConsumer();
		
		sendFinalJob();
		
		showFooter();
	}

	private void initTimer() {
		startTime = System.currentTimeMillis();
	}
	
	private void startProducers() {
		Thread producer1 = new Thread(createProducerThread());
		Thread producer2 = new Thread(createProducerThread());
		Thread producer3 = new Thread(createProducerThread());
		
		producerThreads.add(producer1);
		producerThreads.add(producer2);
		producerThreads.add(producer3);
		
		producer1.start();
		producer2.start();
		producer3.start();
	}

	private Runnable createProducerThread() {
		
		return new Runnable() {
			@Override
			public void run() {
				Random random = new Random();
				for (int i=0; i < numbersOfJobs; i++) {
					System.out.println(Thread.currentThread().getName() + " Producing job:" + i);
					
					int value = random.nextInt(Job.MAX_JOB_VALUE);

					Job job = new Job(jobCounter.getAndIncrement(), value);
					jobsProcessor.produceJob(job);
					
					checker.acumulateProducedValues(value);	
				}
				latch.countDown();
			}
		};
	}

	private void startConsumer() throws InterruptedException {
		Thread consumerThread = new Thread(createConsumerThread());		
		consumerThread.start();

	}

	private Runnable createConsumerThread()  {
		
		return new Runnable() {
			@Override
			public void run() {
				Job job;
				
				do {
					job = jobsProcessor.consumeJob();
					checker.acumulateConsumedValue(job.getValue());
					System.out.println("Consumed job " + job.getId());
					
					pause(50);
				} while (!jobsProcessor.stopQueueProcessing(job));
				latch.countDown();
				
			}
		};

	}
	
	private void pause(int milis) {
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void sendFinalJob() {
		try {
			awaitForProducerThreadToFinish();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Job finalJob = new Job(true);
		jobsProcessor.produceJob(finalJob);
	}
	
	private void awaitForProducerThreadToFinish() throws InterruptedException {
		for (Thread t: producerThreads) {
			t.join();
		}
	}

	private void showFooter() {
		try {
			latch.await();
			System.out.println(jobsProcessor.getClass().getSimpleName());
			System.out.println(
					"Excecution result: producer=" + jobsProcessor.obtainTotalProduced() + 
					", consumer=" + jobsProcessor.obtainTotalConsumed());
			System.out.println(checker.toString());
			System.out.println("Elapsed time: " + elapsedExecutionTime());
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private long elapsedExecutionTime() {
		return System.currentTimeMillis() - startTime;
	}
	
	public Checker getChecker() {
		return this.checker;
	}
	
	public JobsProcessor getJobProcessor() {
		return this.jobsProcessor;
	}
}



