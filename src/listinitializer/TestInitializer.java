package listinitializer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestInitializer {
	private int numberOfList;
	private int numberOfElementsPerList;
	private InitializerWithCountDownLatch initializer;
	private Checker checker;

	@Test
	void testInitializerWithCountDownLatch() {
		numberOfList = 5;
		numberOfElementsPerList = 1000;
		runInitializerAndChecker(new InitializerWithCountDownLatch(numberOfList, numberOfElementsPerList));
		
		runAsserts();
	}

	@Test
	void testInitializerWithJon() {
		numberOfList = 5;
		numberOfElementsPerList = 1000;
		runInitializerAndChecker(new InitializerWithJoin(numberOfList, numberOfElementsPerList));
		
		runAsserts();
	}
	
	private void runInitializerAndChecker(InitializerWithCountDownLatch initializer) {
		this.initializer = initializer;
		this.initializer.startInitialization();
		checker = new Checker(this.initializer.getList());
	}
	
	private void runAsserts() {
		assertEquals(numberOfList, checker.obteinNumberOfList(), "The number of generated lists does not match");
		assertEquals(numberOfElementsPerList * numberOfList, checker.obteinNumberOfElements(), "The number of elements does not match");
		assertEquals(initializer.getSumOfElements(), checker.obteinSumOfElements(), "The sum of elements does not match");
	}
}
