/**
 * 
 */
package edu.lamar.server.aos;

import java.io.IOException;

/**
 * @author user
 *
 */
public class Server extends AbstractServer {

	private int TIME_STAMP = 0;
	public Server(int port) {
		super(port);
	}

	/* (non-Javadoc)
	 * @see edu.lamar.server.aos.AbstractServer#handleMessageFromClient(java.lang.Object, edu.lamar.server.aos.ConnectionToClient)
	 */
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		System.out.println("msg: " + msg);
		sendToAllClients(msg);
	}
	public static void main(String[] args) {
		Server myServer = new Server(5555);
		try {
			myServer.listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
