package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;

import model.CommandData;
import model.ModuleBase;
import model.TemperatureResponse;

import org.json.JSONObject;

import usecase.ThermalDataSector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import constant.Modules;
import constant.State;
import constant.ThermalCommands;
import constant.ThermalKeys;
import generic.RoverClientRunnable;
import generic.RoverServerRunnable;
import generic.RoverThreadHandler;

public class ThermalServerChild extends RoverClientRunnable {

	private RoverServerRunnable parent;

	private Socket socket;

	private String commandStr;

	private String moduleName;

	private double sensorTemp;

	private String responseString;

	// private String commStr;

	private String moduleCommand;

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public ThermalServerChild() throws IOException {
		super(0, null);
	}
	
	public String getState(String moduleName){
	  
	  System.out.println("getState()" + moduleName);
	  moduleName = moduleName.toUpperCase();
	  System.out.println("Size: "+ThermalDataSector.getTempDataSector().getModuleMap().size());
	  System.out.println(ThermalDataSector.getTempDataSector().getModuleMap().get(Modules.valueOf(moduleName)));
	  return ThermalDataSector.getTempDataSector().getModuleMap().get(Modules.valueOf("NAVCAM")).getHeaterState().toString();
	}

	public void checkCommand() {
		// String jsonString =
		// "{\"data\":{\"name\":\"Chemcam\",\"Command\":\"CURRENT_TEMP\"}}";
		// String jsonString =
		// "{'data':{'name':'Chemcam','Command':'CURRENT_TEMP'}}";
		org.json.JSONObject jObject;

		// converting string to JSON
		try {
			jObject = new org.json.JSONObject(commandStr);
			// org.json.JSONObject data = jObject.getJSONObject("data"); // get
			// data object
			Object json = new org.json.JSONTokener(commandStr).nextValue();
			if (json instanceof JSONObject) {
				// org.json.JSONObject data = jObject.getJSONObject("data");
				Iterator<String> keys = jObject.keys();
				// System.out.println("All keys from JSON String:");
				while (keys.hasNext()) {
					String key = keys.next().toUpperCase();
					switch (ThermalKeys.valueOf(key)) {
					case NAME:
						moduleName = jObject.getString("name").toUpperCase();
						break;

					case COMMAND:
						moduleCommand = jObject.getString("command")
								.toUpperCase();
						break;

					case TEMPERATURE:
						sensorTemp = jObject.getDouble("temperature");
						break;

					default:
						System.out.println("KEY: " + key + "NOT CAPTURED");
					}
					// System.out.println(key +" " +
					// jObject.getString(key).toString());
				}
			}
			// moduleName = data.getString("name"); // get the name from data.
			// moduleCommand = data.getString("command");
			// sensorTemp = data.getDouble("");
			// chANGE
			// System.out.println("Name: "+moduleName);
			// System.out.println("Command: " + moduleCommand);

		} catch (org.json.JSONException e) {
			e.printStackTrace();
		} 

	}

	public boolean processCommand() {

		boolean bResult = false;
		checkCommand();
		ModuleBase modBase = null;

		if (moduleCommand != null && moduleCommand.isEmpty() != true) {
			System.out.println("******in server:" + moduleCommand);
			switch (ThermalCommands.valueOf(moduleCommand)) {
			case OUTSIDE_TEMPERATURE:
				responseString = ThermalDataSector.getTempDataSector()
						.getOutsideTemperature();
				// System.out.println("CURRENT_TEMPERATURE" +responseString);
				break;
			case CURRENT_TEMPERATURE:
				if (moduleName != null && moduleName.isEmpty() != true) {
					modBase = ThermalDataSector.getTempDataSector().getModule(
							Modules.valueOf(moduleName.toUpperCase()));
				}
				modBase.getCurrTemp();
				TemperatureResponse tempResp = new TemperatureResponse(
						moduleName, modBase.getCurrTemp());
				responseString = tempResp.jsonify();
				// System.out.println("OUTSIDE_EMPERATURE" +responseString);
				break;
			case CURRENT_TEMPERATURES:
				responseString = ThermalDataSector.getTempDataSector()
						.getModTemps();
				// System.out.println("CURRENT_TEMPERATURES" +responseString);
				break;
			default:
				break;
			}
		} else {
			bResult = true;
		}
		ThermalDataSector.getTempDataSector().getModTemps();
		//
		return bResult;
	}

	@Override
	public void run() {

		try {
			// read from socket to ObjectInputStream object
			ObjectInputStream ois = new ObjectInputStream(this.getSocket()
					.getInputStream());
			// convert ObjectInputStream object to String
			commandStr = (String) ois.readObject();
			System.out.println("Server: Message Received from Client - "
					+ commandStr.toUpperCase());
			if (processCommand() != true) {
				// create ObjectOutputStream object
				ObjectOutputStream oos = new ObjectOutputStream(this
						.getSocket().getOutputStream());
				// write object to Socket
				oos.writeObject(responseString);
				// close resources
				oos.close();
			} else {
				// System.out.println(Modules.valueOf(moduleName.toUpperCase()));
				ModuleBase moduleBase = ThermalDataSector.getTempDataSector()
						.getModule(Modules.valueOf(moduleName.toUpperCase()));
				System.out.println(Modules.valueOf(moduleName.toUpperCase()));
			
					moduleBase.setCurrTemp(sensorTemp);
					if (sensorTemp <= moduleBase.getMinTemp()) {
						if (moduleBase.isHeater()
								&& moduleBase.getHeaterState()
										.equals(State.OFF)) {
							System.out
									.println("*******************HEATER_TURN_ON************************");
							initCommandClient(ThermalCommands.THRM_HEATER_ON);
						} else if (moduleBase.isCooler()
								&& moduleBase.getCoolerState()
										.equals(State.OFF)) {
							System.out
									.println("*******************COOLER_TURN_OFF************************");
							initCommandClient(ThermalCommands.THRM_COOLER_OFF);
						}
					} else if (sensorTemp >= moduleBase.getMaxTemp()) {
						if (moduleBase.isHeater()
								&& moduleBase.getHeaterState().equals(State.ON)) {
							System.out
									.println("********************HEATER_TURN_OFF***********************");
							initCommandClient(ThermalCommands.THRM_HEATER_OFF);

						} else if (moduleBase.isCooler()
								&& moduleBase.getCoolerState()
										.equals(State.OFF)) {
							System.out
									.println("*******************COOLER_TURN_ON***********************");
							initCommandClient(ThermalCommands.THRM_COOLER_ON);

						}
					}
				}
			
			ois.close();

			// getRoverServerSocket().closeSocket();
			// terminate the server if client sends exit request
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void initCommandClient(ThermalCommands thermalCommand) {
		CommandData commResp = new CommandData(moduleName, thermalCommand
				.toString().toUpperCase());
		String data = commResp.jsonify();
		ThermalCommandClient thermalCommandClient = null;
		try {
			thermalCommandClient = new ThermalCommandClient(9017, null);
			thermalCommandClient.setData(data);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		Thread client = RoverThreadHandler.getRoverThreadHandler()
				.getNewThread(thermalCommandClient);
		client.start();
	}

	public RoverServerRunnable getParent() {
		return parent;
	}

	public void setParent(RoverServerRunnable parent) {
		this.parent = parent;
	}

}
