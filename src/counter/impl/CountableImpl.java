package counter.impl;

import counter.Countable;

public class CountableImpl implements Countable {
	int counter  = 0;
	
	@Override
	public void increment() {
		counter++;

	}

	@Override
	public int obtainFinalValue() {
		return counter;
	}

	@Override
	public String obtainDescription() {
		return "No thread safe implementation";
	}

}
