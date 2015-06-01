package generic;

import java.io.IOException;

public abstract class RoverServerRunnable implements Runnable{
	
	private RoverServerSocket roverServerSocket;
	
	/**
	 * Directly assign the port number to the Rover Server 
	 * @param port
	 * @throws IOException
	 */
	public RoverServerRunnable(int port) throws IOException{
		setRoverServerSocket(port);
	}
	
	/**
	 * Closes all all the server's open sockets 
	 * @throws IOException
	 */
	public void closeAll() throws IOException{		
		if(roverServerSocket != null)
			roverServerSocket.closeAll();
	}
	
	/**
	 * returns the object of RoverServerSocket that has been created by the setRoverServerSocket(int port)
	 * @return RoverServerSocket
	 */
	public RoverServerSocket getRoverServerSocket() {
		return roverServerSocket;
	}

	/**
	 * Creates the RoverServerSocket instance and assigns the given port to it.
	 * @param port
	 * @throws IOException
	 */
	private void setRoverServerSocket(int port) throws IOException {
		this.roverServerSocket = new RoverServerSocket(port);
	}
		
}
