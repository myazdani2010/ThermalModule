package usecase;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Logger;

import model.ModuleBase;
import model.TemperatureResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import constant.Modules;
import constant.State;

/**
 * ThermalDataSector is <b>Singleton pattern</b> that restricts the instantiation of a class to one object
 * that implements Rover module with their temperature detail and
 * creates list Map of temperature chart.
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
public class ThermalDataSector {

	private static ThermalDataSector tempDataSector;

	private Map<Modules, ModuleBase> moduleMap;

	private String filePath = System.getProperty("user.dir") + "\\data\\";

	/**
	 * tempChart map holds 1480 temperatures indicating a sol temp in Mars key
	 * is minute from 10 to 1480 increments by 10 (ex: 10, 20, 30, ... , 1480)
	 */	
	private Map<Integer, Float> tempChart;

	private ThermalDataSector() {

		setModuleMap(new HashMap<Modules, ModuleBase>());
		populateModuleMap();
		init();

	}

	private void init() {
		// initMap();
		initTempChart();
	}

	/**
	 * Creates a new <b>Map</b> of temperatures by reading all the temps list
	 * from JSON file "E:\data\temperatures.json"
	 */
	private void initTempChart() {

		System.out.println("initTempChart() called");

		this.tempChart = new TreeMap<Integer, Float>();

		String jsonFile = filePath + "temperatures.json";

		// JSONParser is used to parse the data
		JSONParser parser = new JSONParser();

		try {
			// parsing all the objects to the Object and then to JSONObject.
			Object tempObjects = parser.parse(new FileReader(jsonFile));
			JSONObject jsonObjects = (JSONObject) tempObjects;

			// from the JSONObject create the array of temperature
			JSONArray tempArray = (JSONArray) jsonObjects.get("temps");

			for (Object o : tempArray) {
				JSONObject jo = (JSONObject) o;

				tempChart.put(
						new Integer(Integer.parseInt(jo.get("minute")
								.toString())),
						new Float(Float.parseFloat(jo.get("temp").toString())));
			}

		} catch (ParseException e1) {
			e1.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Map tempChart created with " + tempChart.size()
				+ " Objects");

	}

	/**
	 * returns the singleton <b>ThermalDataSector</b> object if it exists. 
	 * It will create the new object if existing thermalDatasector found null 
	 * 
	 * @return ThermalDataSector
	 */
	public static ThermalDataSector getTempDataSector() {

		// System.out.println("getTempDataSector() called");

		if (tempDataSector == null) {
			tempDataSector = new ThermalDataSector();
		}
		return tempDataSector;
	}

	public Map<Modules, ModuleBase> getModuleMap() {
		return moduleMap;
	}

	public ModuleBase getModule(Modules mod) {
		return moduleMap.get(mod);
	}

	/**
	 * returns outside temperature of Mars by reading the temperature chart from JSON file.
	 * JSON Chart contains list of 148 temperatures that is demonstrating the Mars temp changing every 10min.
	 * 
	 * @return JSON format String contains current outside temp of Mars
	 */
	public String getOutsideTemperature() {

		System.out.println("GetTemperature() called");

		Calendar cal = Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String currentTime = sdf.format(cal.getTime());
		String[] currTime = currentTime.split(":");

		int minutes = Integer.parseInt(currTime[0]) * 60
				+ Integer.parseInt(currTime[1]);

		minutes = minutes - (minutes % 10);

		// System.out.println(minutes);
		// System.out.println("Returned current temperature :"+tempChart.get(minutes));

		TemperatureResponse tempResp = new TemperatureResponse("ATMOSPHERE",
				tempChart.get(minutes));
		return tempResp.jsonify();

		// return tempChart.get(minutes).toString();

	}

	/**
	 * Iterates through the entry map which contains all the rover modules with their current temperature. 
	 * It returns JSON formated String which contains the parent key 'list' as JSON array.
	 * <br><br><b>kyes:</b><br>
	 * <b>list:</b> holds the array of JSON objects that contains name of the module and its current temp <br>
	 * <b>name:</b> holds the module name<br>
	 * <b>temperature:</b> holds the current temperature of a particular module at that moment <br>
	 * 
	 * @return String in JSON format 
	 * Example: 
	 * {"list":[{
  	 *		"name": "SAM",
  	 *		"temperature": "2.0"
	 *		},
	 *		{
  	 *		"name": "MOBILITY1",
  	 *		"temperature": "0.0"
	 *		}
	 *	]
	 * }
	 * 
	 */
	public String getModTemps() {
		String output = "{\"list\":[";
		for (Entry<Modules, ModuleBase> entry : moduleMap.entrySet()) {
			TemperatureResponse tempResp = new TemperatureResponse(entry
					.getKey().toString(), entry.getValue().getCurrTemp());
			output += tempResp.jsonify();
			output += ",\n";
		}
		output += "]}";
		//System.out.println(output);
		return output;
	}

	/**
	 * Creates the tempRange for each available modules that are being fetched
	 * from ModuleBase Enum. The tempRange and module name is being added to the
	 * modeuleMap. Refer to moduleMap object to get all the modules with their
	 * temperature range.
	 */
	private void initMap() {

		System.out.println("initMap() called");

		// Modules is Enum holding all the rover's modules name
		for (Modules mod : Modules.values()) {
			ModuleBase moduleBase = new ModuleBase();

			setTempRange(mod, moduleBase);
			moduleMap.put(mod, moduleBase);
			// System.out.println(mod);
		}

		System.out.println("Map moduleMap is created with " + moduleMap.size()
				+ " Objects.");
	}

	// set the temp range of each module by reading it from JSON file
	public void setTempRange(Modules mod, ModuleBase moduleBase) {

	}

	/**
	 * sets the module map which contains name of Modules enum and ModuleBase model
	 * 
	 * @param Map that contains enum Modules as key and ModuleBase object as its value
	 */
	public void setModuleMap(Map<Modules, ModuleBase> hashMap) {
		this.moduleMap = hashMap;

	}

	/**
	 * This method populates the ModuleBase objects in the moduleMap object by
	 * reading the data from the JSON file.
	 */
	private void populateModuleMap() {

		System.out.println("populateModuleMap() called");

		this.moduleMap = new HashMap<Modules, ModuleBase>();

		String jsonFile = filePath + "device_temp.json";

		// JSONParser is used to parse the data
		JSONParser parser = new JSONParser();

		try {
			// parsing all the objects to the Object and then to JSONObject.
			Object tempObjects = parser.parse(new FileReader(jsonFile));
			JSONObject jsonObjects = (JSONObject) tempObjects;

			// from the JSONObject create the array of temperature
			JSONArray tempArray = (JSONArray) jsonObjects.get("instruments");

			for (Object o : tempArray) {

				JSONObject jo = (JSONObject) o;

				// System.out.println(jo.get("maxTemp").toString());
				ModuleBase moduleBase = new ModuleBase();
				moduleBase.setMaxTemp(new Double(Double.parseDouble(jo.get(
						"maxTemp").toString())));
				moduleBase.setMinTemp(new Double(Double.parseDouble(jo.get(
						"minTemp").toString())));
				moduleBase.setHeater(new Boolean(Boolean.parseBoolean(jo.get(
						"heater").toString())));
				moduleBase.setCooler(new Boolean(Boolean.parseBoolean(jo.get(
						"cooler").toString())));
				moduleBase.setCoolerState(State.OFF);
				moduleBase.setHeaterState(State.OFF);

				for (Modules mod : Modules.values()) {
					if (mod.toString().equals(
							jo.get("name").toString().toUpperCase())) {
						moduleMap.put(mod, moduleBase);
					} else {
						moduleMap.put(mod, new ModuleBase());
					}

				}

			}

		} catch (ParseException e1) {
			e1.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("The moduleBase in moduleMap is populated. moduleMap size is: "
						+ moduleMap.size());

	}

}
