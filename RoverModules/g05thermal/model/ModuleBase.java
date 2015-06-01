package model;

import constant.State;

public class ModuleBase {
	
	private double currTemp;
	
	private double minTemp;
	
	private double maxTemp;
	
	/**
	 * will be true if the module is having heater
	 */
	private boolean isHeater;
	/**
	 * 
	 */
	private State heaterState;
	
	/**
	 * will be true if the module is having cooler
	 */
	private boolean isCooler;
	
	private State coolerState;
	
	/**
	 * default constructor keeps the heater state and cooler state to OFF 
	 */
	public ModuleBase(){
		this.heaterState = heaterState.OFF;
		this.coolerState = coolerState.OFF;
	}

	public double getCurrTemp() {
		return currTemp;
	}

	public void setCurrTemp(double currTemp) {
		this.currTemp = currTemp;
	}

	public double getMinTemp() {
		return minTemp;
	}

	public void setMinTemp(double minTemp) {
		this.minTemp = minTemp;
	}

	public double getMaxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(double maxTemp) {
		this.maxTemp = maxTemp;
	}

	public boolean isHeater() {
		return isHeater;
	}

	public void setHeater(boolean isHeater) {
		this.isHeater = isHeater;
	}

	public State getHeaterState() {
		return heaterState;
	}

	public void setHeaterState(State heaterState) {
		this.heaterState = heaterState;
	}

	public boolean isCooler() {
		return isCooler;
	}

	public void setCooler(boolean isCooler) {
		this.isCooler = isCooler;
	}

	public State getCoolerState() {
		return coolerState;
	}

	public void setCoolerState(State coolerState) {
		this.coolerState = coolerState;
	}

}
