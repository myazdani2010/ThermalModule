package model;

import constant.State;

public class ModuleBase {
	
    /**
     * stores the c
     */
	private double currTemp;
	
	private double minTemp;
	
	private double maxTemp;
	
	/**
	 * will be true if the module is having heater
	 */
	private boolean isHeater;
	/**
	 * Defines the current status of the heater if there is one.
	 */
	private State heaterState;
	
	/**
	 * will be true if the module is having cooler
	 */
	private boolean isCooler;
	/**
	 * Defines the current status of the cooler if there is one. 
	 */
	private State coolerState;
	
	/**
	 * default constructor keeps the heater state and cooler state to OFF 
	 */
	public ModuleBase(){
		this.heaterState = State.OFF;
		this.coolerState = State.OFF;
	}

	/**
	 * Returns the current temperature of the device/module
	 * @return double
	 */
	public double getCurrTemp() {
		return currTemp;
	}

	/**
	 * Sets the current temperature given as part of the parameter argument.
	 * @param double 
	 */
	public void setCurrTemp(double currTemp) {
		this.currTemp = currTemp;
	}

	/**
	 * Returns the minimum allowable temperature of the module. Since every module in
	 * the rover has got different temperature range, the minimum temperature varies for each object.
	 * 
	 * @return double
	 */
	public double getMinTemp() {
		return minTemp;
	}

	/**
	 * Sets the minimum allowable temperature of the module. Since every module in
	 * the rover has got different temperature range, the minimum temperature varies for each object.
	 * 
	 * @param double
	 */
	public void setMinTemp(double minTemp) {
		this.minTemp = minTemp;
	}

	/**
	 * Returns the maximum allowable temperature of the module. Since every module in
	 * the rover has got different temperature range, the maximum temperature varies for each object.
	 * 
	 * @return double
	 */
	public double getMaxTemp() {
		return maxTemp;
	}
	
	/**
	 * Returns the maximum allowable temperature of the module. Since every module in
	 * the rover has got different temperature range, the maximum temperature varies for each object.
	 * 
	 * @param double
	 */
	public void setMaxTemp(double maxTemp) {
		this.maxTemp = maxTemp;
	}

	/**
	 * Will return true if the module has got heater. Since all the modules in the rover does not have
	 * heater, we need this attribute to keep track of the modules who are having heater or not.
	 * @return boolean
	 */
	public boolean isHeater() {
		return isHeater;
	}

	/**
	 * Set to true if the device is having heater. Since all the modules in the rover does not have
	 * heater, we need this attribute to keep track of the modules who are having heater or not.
	 * @param boolean
	 */
	public void setHeater(boolean isHeater) {
		this.isHeater = isHeater;
	}
	
	/**
	 * Returns the state of the heater if there is one. The state can be ON/OFF
	 * @return State
	 */
	public State getHeaterState() {
		return heaterState;
	}

	/**
	 * Sets the state of the heater if there is one. The state can be ON/OFF
	 * @param State
	 */
	public void setHeaterState(State heaterState) {
		this.heaterState = heaterState;
	}

	/**
	 * Will return true if the module has got cooler. Since all the modules in the rover does not have
	 * cooler, we need this attribute to keep track of the modules who are having cooler or not.
	 * @return boolean
	 */
	public boolean isCooler() {
		return isCooler;
	}

	/**
	 * Set to true if the device is having cooler. Since all the modules in the rover does not have
	 * cooler, we need this attribute to keep track of the modules who are having cooler or not.
	 * @param boolean
	 */
	public void setCooler(boolean isCooler) {
		this.isCooler = isCooler;
	}

	/**
	 * Returns the state of the cooler if there is one. The state can be ON/OFF
	 * @return State
	 */
	public State getCoolerState() {
		return coolerState;
	}

	/**
	 * Sets the state of the cooler if there is one. The state can be ON/OFF
	 * @param State
	 */
	public void setCoolerState(State coolerState) {
		this.coolerState = coolerState;
	}

}
