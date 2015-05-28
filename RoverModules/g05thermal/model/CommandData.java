package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CommandData {
	
	String command;
	String name;
	
	public CommandData(String deviceName, String commandName){
		setCommandName(commandName);
		setDeviceName(deviceName);
	}
	
	public String getCommandName() {
		return command;
	}
	public void setCommandName(String commandName) {
		this.command = commandName;
	}
	public String getDeviceName() {
		return name;
	}
	public void setDeviceName(String deviceName) {
		this.name = deviceName;
	}
	
	public String jsonify(){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}

}
