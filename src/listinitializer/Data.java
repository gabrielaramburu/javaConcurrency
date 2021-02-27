package listinitializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Data {
	private static final int MAX_GENERATED_VALUE = 1000;
	
	private List<Integer> list = new ArrayList<Integer>();
	
	public List<Integer> getList() {
		return Collections.unmodifiableList(list);
	}
	
	public int getSize() {
		return list.size();
	}
	
	public void initializeData(int numberOfElements) {
		System.out.println("Start initialization...");
		for (int i=0; i< numberOfElements;i++) {
			list.add(new Random().nextInt(MAX_GENERATED_VALUE));
			
			pause(); //just for simulation some work
		}
		System.out.println("List initialized. Size: " + list.size());
	}
	
	private void pause() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
