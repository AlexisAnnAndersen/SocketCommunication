package alexisandersen;

import java.io.*;
import java.net.*;

public class SocketCommunication {
	
	public static class ConnectionHandler extends Thread {
		public Socket connection;
		
		public void run() {
			try {
				
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while(in.ready())
					out.append(in.readLine() + "\n");
				out.append("all done\n");
				out.flush();
				connection.close();
			}
			catch (Exception e) {
				System.err.println(e.getLocalizedMessage());
			}			
		}		
	}
	
	public static void main(String args[]) {
		System.out.println("Waiting for the client request");
		SocketCommunication obj = new SocketCommunication();
		try {
			ServerSocket server = new ServerSocket(9999);
			while (!server.isClosed()) {
				Socket socket = server.accept();
				ConnectionHandler handler = new ConnectionHandler();
				handler.connection = socket;
				handler.start();
			}
			server.close();			
		}
		catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}
    }
}
