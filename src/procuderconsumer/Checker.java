package procuderconsumer;

public class Checker {
	private int totalProduced = 0;
	private int totalConsumed = 0;
	
	//public synchronized void acumulateProducedValues(int value) {
	public void acumulateProducedValues(int value) {
		this.totalProduced += value;
	}
	
	//public synchronized void acumulateConsumedValue(int value) {
	public void acumulateConsumedValue(int value) {
		this.totalConsumed += value;
	}

	public int getTotalProduced() {
		return totalProduced;
	}

	public int getTotalConsumed() {
		return totalConsumed;
	}

	@Override
	public String toString() {
		return "Checker [totalProduced=" + totalProduced + ", totalConsumed=" + totalConsumed + "]";
	}
	
	
}
