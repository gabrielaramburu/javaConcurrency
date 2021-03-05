package procuderconsumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import procuderconsumer.impl.BlockingQueueImpl;

public class TestProducerConsumer {

	@RepeatedTest(10)
	void testBlockingQueueImpl() throws InterruptedException {
		ProducerConsumer producerConsumer = new ProducerConsumer(new BlockingQueueImpl());
		producerConsumer.start();
		

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
