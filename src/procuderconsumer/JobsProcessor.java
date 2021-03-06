package procuderconsumer;

public interface JobsProcessor {
	
	public void produceJob(Job job);
	public Job consumeJob();
	
	public int obtainTotalProduced();
	public int obtainTotalConsumed();
	public boolean stopQueueProcessing(Job signal);
}
