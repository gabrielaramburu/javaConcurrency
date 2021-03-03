package listinitializer;

import java.util.List;

public class Checker {
	private List<Data> generatedData;
	private int numberOfElements = 0;
	private int sumOfElements = 0;
	
	
	
	public Checker(List<Data> generatedData) {
		this.generatedData = generatedData;
		processResult();
	}
	
	private void processResult() {
		for (Data data:  generatedData) {
			for (Integer num: data.getList()) {
				sumOfElements = sumOfElements + num;
				numberOfElements++;
			}
		}
	}
	
	public int obteinNumberOfList() {
		return this.generatedData.size();
	}
	
	public int obteinNumberOfElements() {
		return this.numberOfElements;
	}
	
	public int obteinSumOfElements() {
		return this.sumOfElements;
	}

	

	
}
