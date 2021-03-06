package producerconsumer;

public class Job {
	protected static final int MAX_JOB_VALUE = 100;
	
	private int id;
	private int value;
	private boolean finalJob;
	
	public Job(int id, int value) {
		this.id = id;
		this.value = value;
		this.finalJob = false;
	}
	
	public Job(boolean finalJob) {
		this(0,0);
		this.finalJob = finalJob;
	}
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}


	public boolean isFinalJob() {
		return finalJob;
	}

	public void setFinalJob(boolean finalJob) {
		this.finalJob = finalJob;
	}

	

	
}
