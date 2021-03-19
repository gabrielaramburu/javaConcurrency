package serversocket;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.Timeout;

@TestInstance(Lifecycle.PER_CLASS)
public class TestServer {
	private static final int PORT = 8009;
	private static final int NUMBER_OF_CLIENT = 5;
	
	private Server server = new Server(PORT);
	private List<Thread> clientThreads;
	
	@BeforeAll
	void startServer() {
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				server.start();
			}
		}); 	
		thread.start();
		
		System.out.println("Test->Waiting for server...");
		waitUntilServerIsReady();
		System.out.println("Test->Server is ready.");
	}
	
	private void waitUntilServerIsReady() {
		while(!server.isReady());
	}

	@Test
	@Timeout(value = 600, unit = TimeUnit.MILLISECONDS)
	void testServer() throws IOException {
		initializeClientThreadsList();
		startAllClientThread();
		waitForAllThreadToFinish();
		
	}
	
	private void initializeClientThreadsList() {
		clientThreads = new ArrayList<Thread>();
		
		for (int i = 0; i< NUMBER_OF_CLIENT; i++) {
			clientThreads.add(createClientThread());
		}
	}
	
	private void startAllClientThread() {
		for (Thread thread: clientThreads) {
			thread.start();
		}
	}
	
	private void waitForAllThreadToFinish() {
		for (Thread thread: clientThreads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private Thread createClientThread() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				Client client = new Client(PORT);
				String response = client.communicateWithServer("Time: " + System.currentTimeMillis() + "\n");
				assertTrue(response.contains("Message from server")); 
			}}) ;
		
		return thread;
	}

	@AfterAll
	void stopServer() {
		System.out.println("Test->Stoping server.");
		server.stop();
	}
}
