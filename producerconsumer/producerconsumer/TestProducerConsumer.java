package producerconsumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import producerconsumer.impl.BlockingQueueImpl;
import producerconsumer.impl.QueueLowLevelImpl;

public class TestProducerConsumer {
	protected static final int MAX_JOBS = 10;
	protected static final int NO_JOBS = 0;
	
	@DisplayName("ProducerConsumer using BlockingQueue, normal excecution.")
	@RepeatedTest(5)
	void testBlockingQueueImpl() throws InterruptedException {
		ProducerConsumer producerConsumer = new ProducerConsumer(new BlockingQueueImpl(), MAX_JOBS);
		producerConsumer.start();
		
		assertExecutionOfProducerConsumer(producerConsumer);
		
	}
	
	@DisplayName("ProducerConsumer using low level synchronization idioms, normal excecution.")
	@RepeatedTest(5)
	void testQueueLowLevelImpl() throws InterruptedException {
		int maxCapacityOfQueue = 10;
		ProducerConsumer producerConsumer = new ProducerConsumer(new QueueLowLevelImpl(maxCapacityOfQueue), MAX_JOBS);
		producerConsumer.start();
		
		assertExecutionOfProducerConsumer(producerConsumer);
	}
	
	
	@DisplayName("Low level implemmentation with no produced job.")
	@Test
	void testQueueWithNoProducedJobs() throws InterruptedException {
		int maxCapacityOfQueue = 10;
		ProducerConsumer producerConsumer = new ProducerConsumer(new QueueLowLevelImpl(maxCapacityOfQueue), NO_JOBS);
		producerConsumer.start();
		
		assertExecutionOfProducerConsumer(producerConsumer);
	}
	
	@DisplayName("Low level implemmentation with a large queue.")
	@Test
	void testQueueWithBigCapacity() throws InterruptedException {
		int maxCapacityOfQueue = 100;
		ProducerConsumer producerConsumer = new ProducerConsumer(new QueueLowLevelImpl(maxCapacityOfQueue), MAX_JOBS);
		producerConsumer.start();
		
		assertExecutionOfProducerConsumer(producerConsumer);
	}
	
	@DisplayName("Low level implemmentation with a small queue.")
	@Test
	void testQueueWithSmallCapacity() throws InterruptedException {
		int maxCapacityOfQueue = 2;
		ProducerConsumer producerConsumer = new ProducerConsumer(new QueueLowLevelImpl(maxCapacityOfQueue), MAX_JOBS);
		producerConsumer.start();
		
		assertExecutionOfProducerConsumer(producerConsumer);
	}
	
	private void assertExecutionOfProducerConsumer(ProducerConsumer producerConsumer) {
		assertEquals(
				producerConsumer.getChecker().getTotalProduced(), 
				producerConsumer.getJobProcessor().obtainTotalProduced(), 
				"The values produced are the same in both process.");
		
		assertEquals(
				producerConsumer.getChecker().getTotalConsumed(), 
				producerConsumer.getJobProcessor().obtainTotalConsumed(), 
				"The values consumed are the same in both process.");
	}

}
