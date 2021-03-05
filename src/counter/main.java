package counter;

import counter.impl.CountableImpl;
import counter.impl.ThreadSafeCountableImpl;
import counter.impl.ThreadSafeCountableV2Impl;
import counter.impl.ThreadSafeCountableWihtUnestructuredLock;

public class main {

	public static void main(String[] args) {
		RunnerWithExcecutorService testContable;
		
		try {
			testContable = new RunnerWithExcecutorService("Test 1", new CountableImpl(), 1);
			testContable.startTest();
			
			testContable = new RunnerWithExcecutorService("Test 2", new CountableImpl(), 2);
			testContable.startTest();
			
			testContable = new RunnerWithExcecutorService("Test 3", new ThreadSafeCountableImpl(), 2);
			testContable.startTest();
			
			testContable = new RunnerWithExcecutorService("Test 4", new ThreadSafeCountableV2Impl(), 2);
			testContable.startTest();
			
			testContable = new RunnerWithExcecutorService("Test 4", new ThreadSafeCountableWihtUnestructuredLock(), 5);
			testContable.startTest();
			
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
