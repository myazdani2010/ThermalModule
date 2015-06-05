package server;

import generic.RoverClientRunnable;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ThermalCommandClient extends RoverClientRunnable{
	
	private String data;

	public ThermalCommandClient(int port, InetAddress host) throws UnknownHostException {
		super(port, host);
	}

	@Override
	public void run() {
		try{
			ObjectOutputStream oos = null;
		    ObjectInputStream ois = null;
		    Thread.sleep(2000);
	        //for(int i=0; i<5;i++){
            //establish socket connection to server
            //socket = new Socket(host.getHostName(), 9876);
            //write to socket using ObjectOutputStream
            oos = new ObjectOutputStream(getRoverSocket().getNewSocket().getOutputStream());
            //System.out.println("Client: Sending request to Socket Server");
            //if(i==4)oos.writeObject("exit");
            //else 
            //CommandData commandData = new CommandData("", "");
            //String wo = commandData.jsonify();
        	oos.writeObject(data);
        	System.out.println("COMMAND TO MODULES: " +data);
            //read the server response message
            ois = new ObjectInputStream(getRoverSocket().getSocket().getInputStream());
            String message = (String) ois.readObject();
            //System.out.println("Client: Message from Server - " + message.toUpperCase());
            //close resources
            ois.close();
            oos.close();
            Thread.sleep(1000);
	        //}
	        closeAll();
		}	        
        catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception error) {
			System.out.println("Client: Error:" + error.getMessage());
		}		
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	} 
}
