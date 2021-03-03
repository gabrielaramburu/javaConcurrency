package procuderconsumer;

public interface JobsProcessor {
	public void sendJob(Job job);
	public Job processJob();
	
	public int obtainTotalSended();
	public int obtainTotalProcessed();
	public boolean stopQueueProcessing(Job signal);
}
