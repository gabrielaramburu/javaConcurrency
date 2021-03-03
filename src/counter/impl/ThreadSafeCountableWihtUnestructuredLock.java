package counter.impl;

import counter.Countable;

public class ThreadSafeCountableWihtUnestructuredLock implements Countable{

	private int counter = 0;
	
	
	@Override
	public void increment() {
		synchronized (this) {
			counter++;
		}
	}

	@Override
	public int obtainFinalValue() {
		return counter;
	}

	@Override
	public String obtainDescription() {
		return "Thread safe implemmentation using Lock()";
	}

}
