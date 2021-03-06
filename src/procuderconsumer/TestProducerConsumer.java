package procuderconsumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import procuderconsumer.impl.BlockingQueueImpl;
import procuderconsumer.impl.QueueLowLevelImpl;

public class TestProducerConsumer {
	protected static final int MAX_JOBS = 10;
	
	
	@Disabled
	@RepeatedTest(50)
	void testBlockingQueueImpl() throws InterruptedException {
		ProducerConsumer producerConsumer = new ProducerConsumer(new BlockingQueueImpl(), MAX_JOBS);
		producerConsumer.start();
		
		assertExecutionOfProducerConsumer(producerConsumer);
		
	}
	
	@Test
	void testQueueLowLevelImpl() throws InterruptedException {
		ProducerConsumer producerConsumer = new ProducerConsumer(new QueueLowLevelImpl(), MAX_JOBS);
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
