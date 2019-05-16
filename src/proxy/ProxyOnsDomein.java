package proxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Scanner;

 
public class ProxyOnsDomein {

	ConnectionSettings conSettings = new ConnectionSettings();
	private Scanner inServer;
	private PrintWriter outServer;
	final int SERVER_PORT = conSettings.PORT;
	final String SERVER_IP = conSettings.SERVER_IP;
 
	@SuppressWarnings("resource")
	private boolean connectWithServer(String client_id) {
	

		InputStream inStream = null;
		OutputStream outStream = null;
		// Get the socket from the server
		Socket s = null;
		try {

			s = new Socket();
			s.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT), conSettings.timeout);
			inStream = s.getInputStream();
			outStream = s.getOutputStream();

		}
		
		catch (SocketTimeoutException e) {
			System.out.println("Time-out opgetreden in verbinding met server.");
			return false;
		}
		
		catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Open the streams to make the handshake

		inServer = new Scanner(inStream);
		outServer = new PrintWriter(outStream);

		boolean reactionFromServer = true;
		while (reactionFromServer) {
			String reaction = inServer.nextLine();

			if (reaction.equals("Who are you?")) {
				reactionFromServer = false;
				outServer.println(client_id);
				outServer.flush();
			}
		}
		return true;

	}


	public boolean connectClientToServer() {
		String client_id = conSettings.app_ID;
		if (connectWithServer(client_id)) return true; 
		return false;
	}
 
	public String sendRequest(String command, String message) {
		String requestFromId = conSettings.app_ID;
		String requestForId = conSettings.receiver_ID;
		String request = command + ";" + requestFromId + ";" + requestForId + ";" + message;
		System.out.println("Request: " + request);
		outServer.println(request);
		outServer.flush();
		return receiveRequest();
	}

	 
	public void closeConnection() {
	if((inServer ==  null || outServer == null)) return;
	
		inServer.close();
		outServer.close();
	}

 
 
	public String receiveRequest() {
		return inServer.nextLine();
	}
}