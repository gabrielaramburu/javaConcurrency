package serversocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
	private ServerSocket serverSocket;
	private boolean aceptingConnections;
	private boolean serverIsReady;
	private Integer requestNumber = 0;
	
	public Server(int port) {
		try {
			aceptingConnections = true;
			serverIsReady = false;
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void start() {
		serverIsReady = true;
		while (acceptingConnection()) {
			try {
				System.out.println("Server->Accepting conections...");
				Socket socket = serverSocket.accept();
				proccessRequest(socket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Server->Server is stopped.");
	}

	private boolean acceptingConnection() {
		return aceptingConnections;
	}

	public void stop() {
		try {
			System.out.println("Server->Stoping server...");
			aceptingConnections = false;
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isReady() {
		return serverIsReady;
	}
	
	private void proccessRequest(Socket socket) throws IOException {
		System.out.println("Server->Getting message...");
		String message = MessageUtil.readMessage(socket);
		pause(100);
		System.out.println("Server->Received mesagge:" +  message);
		MessageUtil.sendMessage(socket, respose());
	}

	private String respose() {
		requestNumber++;
		return "Message from server " + requestNumber + "\n";
	}

	private void pause(int milis) {
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
