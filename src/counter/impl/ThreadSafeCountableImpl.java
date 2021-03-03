package counter.impl;

import counter.Countable;

public class ThreadSafeCountableImpl implements Countable {

	private int counter = 0;
	
	@Override
	public synchronized void increment() {
		counter++;
	}

	@Override
	public synchronized int obtainFinalValue() {
		return counter;
	}

	@Override
	public String obtainDescription() {
		return "Thread safe immplementation using synchronized in both method";
	}

}
