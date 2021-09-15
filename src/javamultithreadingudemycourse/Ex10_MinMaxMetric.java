package javamultithreadingudemycourse;

public class Ex10_MinMaxMetric {
	//this variables needs to be volatile because jvm asigment/reading is not atomic 
	//on long and double variables
	private volatile long min;
	private volatile long max;

	Ex10_MinMaxMetric() {
		this.min = 0;
		this.max = 0;
	}

	public void addMetric(long metric) {
		synchronized(this) {
			if (metric < min) min = metric;
			if (metric > max) max = metric;	
		}
	}

	public long getMin(){
		return min;
	}

	public long getMax() {
		return max;
	}

}