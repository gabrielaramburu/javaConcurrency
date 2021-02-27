package cpuworker;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread test1 = new Thread(new CPUWorker());
		test1.start();
		
		Thread test2 = new Thread(new CPUWorker());
		test2.start();
		
		Thread test3 = new Thread(new CPUWorker());
		test3.start();
		
		

	}

}
