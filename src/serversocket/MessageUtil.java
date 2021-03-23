package serversocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageUtil {
	public static void sendMessage(Socket socket, String message) throws IOException {
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.print(message);
		out.flush();
	}
	
	public static String readMessage(Socket socket) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		return in.readLine();
	}

}
