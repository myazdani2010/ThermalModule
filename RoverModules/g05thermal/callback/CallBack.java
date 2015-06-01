package callback;

import java.net.UnknownHostException;

import json.Constants;

/**
 * 
 * @author 	Jonathan Young
 *
 */
public class CallBack {
	
	public CallBack() {	}
	
	/**
	 * Creates and instance of CallBackClient and send the message to the Computer Command and Data Subsystem
	 * informing regarding work done.
	 */
	public void done() {
		CallBackClient client = null;
		try {
			client = new CallBackClient(Constants.COMMAND_PORT, null);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		client.run();
	}
}
