package main;

import generic.RoverThreadHandler;

import java.io.IOException;

import json.Constants;
import model.RequestsManager;
import server.ThermalServer;
import usecase.SensorManager;
/**
 * <b>MasterMain is used to run the project.</b> <br>
 * This Class will Create the instance of ThermalServer and starts it 
 * on the given port which is being red from the Constants.THERMAL_PORT
 *  
 * @author <b>Thermal Group</b> (CS537: California State University, Los Angeles) <br><br>
 * 
 * <ul>
 * 	<li> Debasish Guha: debasish.gt@gmail.com </li>
 *  <li> Mahdiye Jamali: mahdiye.jamali@gmail.com </li>
 *  <li> Gayatri Devpal: gayatridevpal18@gmail.com </li>
 *  <li> Mohammad Yazdani: m.yazdani2010@gmail.com </li>
 * </ul>
 */
public class MasterMain {
  
  	/**
  	 * Thermal unique server port number
  	 */
	private static int port = Constants.THERMAL_PORT;	
	
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
	
	/**
	 * this will return the Server port that is being used by Thermal module
	 * @return int
	 */
	public static int getPort(){
		return port;
	}
}
