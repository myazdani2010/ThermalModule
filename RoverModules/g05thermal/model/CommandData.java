package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 * Model class for storing name of the device/ module that is sending the command and command.
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
public class CommandData {
	
	private String command;
	private String name;
	
	/**
	 * Assigns the device name and command instantly while creating the object
	 * 
	 * @param deviceName
	 * @param commandName
	 */
	public CommandData(String deviceName, String commandName){
		setCommandName(commandName);
		setDeviceName(deviceName);
	}
	
	/**
	 * returns the command which has been issued by the instrument/module.
	 * @return String
	 */
	public String getCommandName() {
		return command;
	}
	/**
	 * Sets the command that is being issued by the instrument/module 
	 * @param commandName
	 */
	public void setCommandName(String commandName) {
		this.command = commandName;
	}
	/**
	 * Returns name of the instrument/module that has created the command.
	 * 
	 * @return String
	 */
	public String getDeviceName() {
		return name;
	}
	
	/**
	 * Sets name of the instrument/module that has created the command.
	 * @param deviceName
	 */
	public void setDeviceName(String deviceName) {
		this.name = deviceName;
	}
	
	/**
	 * Converts the String command and instrument/module to JSON String format and returns.
	 * @return String
	 */
	public String jsonify(){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
}
