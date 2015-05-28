package main;

import generic.RoverThreadHandler;

import java.io.IOException;

import model.RequestsManager;
import server.ThermalServer;
import usecase.SensorManager;
/**
 * MasterMain is used to run the project
 *
 */
public class MasterMain {
  	//Port number that has been assigned for Thermal group
	private static int port = 9005;	
	
	public static void main(String[] args) {
		try {
			ThermalServer useCaseServer = new ThermalServer(port);
			Thread server = RoverThreadHandler.getRoverThreadHandler().getNewThread(useCaseServer);

			server.start();
			
			SensorManager.getSensorManager();
			//RequestsManager.getRequestsManager();
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int getPort(){
		return port;
	}
}
