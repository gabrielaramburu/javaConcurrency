package javamultithreadingudemycourse;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;



public class Ex08_HttpServer {
	private static final int NUMBERS_OF_THREADS = 1;
	private static final String INPUT_FILE = "/home/gabriel/workspace/Threads/src/javamultithreadingudemycourse/peaceAndWar.txt";

	public static void main(String[] args) throws IOException {
		String text = new String(Files.readAllBytes(Paths.get(INPUT_FILE)));
		System.out.println("Number of cores " + Runtime.getRuntime().availableProcessors());

		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
		server.createContext("/test", new TestHandler(text));
		Executor executor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);
		server.setExecutor(executor);
		server.start();
	}

	private static class TestHandler implements HttpHandler {
		private String text;

		public TestHandler(String text) {
			this.text = text;
		}

		@Override 
		public void handle(HttpExchange exchange) throws IOException {
			String query = exchange.getRequestURI().getQuery();
			String [] keyValues = query.split("=");
			String action = keyValues[0];
			String value = keyValues[1];

			System.out.println("New request for word " + value + " action " + action);
			if (!action.equals("word")){

				exchange.sendResponseHeaders(400,0);
				return;
			}

			int count = countWord(value);
			byte[] response = Integer.toString(count).getBytes();
			exchange.sendResponseHeaders(200, response.length);
			OutputStream outputStream = exchange.getResponseBody();
			outputStream.write(response);
			outputStream.close();
		}

		private int countWord(String value){
			int i = 0;
			int count = 0;
			while (i != -1) {
				i = this.text.indexOf(value, i);
				if (i != -1) {
					i++;
					count++;	
				} 
			}
			System.out.println("Countig for word " + value + " is " + count);
			return count;
		}
	}


}