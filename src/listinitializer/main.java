package listinitializer;

public class main {

	public static void main(String[] args) {
		Initializer initializer = new Initializer(5, 1000);
		
		try {
			initializer.startInitialization();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
