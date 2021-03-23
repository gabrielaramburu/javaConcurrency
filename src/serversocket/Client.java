package serversocket;

import java.io.IOException;
import java.net.Socket;


public class Client {
	private static final String HOST = "localhost";
	private static final String FAILED = "failed";
	
	private Socket socket;
	
	public Client(int port) {
		try {
			socket = new Socket(HOST, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String communicateWithServer(String message) {
		String response;

		try {
			showHeaderLog(message);
			
			MessageUtil.sendMessage(socket, message);
			response = MessageUtil.readMessage(socket);
			
			showFooterLog(response);
			
		} catch (IOException e) {
			response = FAILED;
			e.printStackTrace();
		}
		return response;
		
	}

	private void showHeaderLog(String message) {
		System.out.println("Client->" + Thread.currentThread().getName() + " sendig: " + message);
	}
	
	private void showFooterLog(String response) {
		System.out.println("Client->" + Thread.currentThread().getName() + " response: " + response);
	}
}
