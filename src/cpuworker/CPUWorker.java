package cpuworker;

public class CPUWorker implements Runnable {
	private int cont = 0;
	
	@Override
	public void run() {
		while (true) {
			try {
				Thread.currentThread().sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cont++;
		}
	}

}
