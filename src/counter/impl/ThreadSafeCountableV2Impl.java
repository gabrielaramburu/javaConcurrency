package counter.impl;

import counter.Countable;

public class ThreadSafeCountableV2Impl implements Countable {

	private int counter = 0;
	
	@Override
	public synchronized void increment() {
		counter++;
	}

	@Override
	public int obtainFinalValue() {
		return counter;
	}

	@Override
	public String obtainDescription() {
		return "Thread safe immplementation using synchronized in just increment() method";
	}

}
