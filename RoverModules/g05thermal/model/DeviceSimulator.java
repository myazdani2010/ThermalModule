package model;

import generic.RoverClientRunnable;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import constant.Modules;
import constant.State;
import constant.ThermalCommands;

/**
 * DeviceSimulator is multithread client class that simulates the client thread for server communication
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
public class DeviceSimulator extends RoverClientRunnable {
	
	/**
	 * Reference id refers to Modules enum object. since every module has a unique name, 
	 * here is considered to be the unique identifier for identifying the running thread on the network.
	 */
  	private Modules id;
  	/**
  	 * Represents the state (ON/OFF) of the module or instrument. 
  	 * State is Enum data type holding list of Strings
  	 * 
  	 */
  	private State state;
  	/**
  	 * Represents the commands that are provided by Thermal module 
  	 * that is being order by other client or module. 
  	 */
	private ThermalCommands thermalCommand;
	/**
	 * Model class object for storing module/instrument and commands associated by them.
	 */
	private CommandData command;
	private int waitTime = 3000;
	private int toggle;
	
	/**
	 * Constructor for defining all the values that is required for creating the DeviceSimulator
	 * and assigning all the values to the instance of the DeviceSimulator.
	 * 
	 * @param port
	 * @param host
	 * @param id
	 * @param toggle
	 * @throws UnknownHostException
	 */
	public DeviceSimulator(int port, InetAddress host, Modules id, int toggle)
		throws UnknownHostException {
	super(port, host);
	this.id = id;
	setToggle(toggle);
	startUp();
	}
	/**
	 * Returns the Stage of the DeviceSimulator Object. State can be String ON/OFF
	 * @return Stage
	 */
	public State getState() {
		return state;
	}

	/**
	 * Sets the Stage of the DeviceSimulator Object. State can be String ON/OFF
	 * @param Stage
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * Returns the Modules of the DeviceSimulator Object. Modules is Enum data type
	 * which is all the modules name of the Rover in form of String. 
	 * @return Modules
	 */
	public Modules getId() {
		return id;
	}

	/**
	 * Sets the Modules of the DeviceSimulator Object. Modules is Enum data type
	 * which is all the modules name of the Rover in form of String. 
	 * @param Modules
	 */
	public void setId(Modules id) {
		this.id = id;
	}

	/**
	 * Returns the ThermalCommands of the DeviceSimulator object. ThermalCommands is 
	 * Enum of Strings that is holding all the comrades handled by Thermal module and 
	 * issued by other module(s)
	 * @return ThermalCommands
	 */
	public ThermalCommands getThermalCommand() {
		return thermalCommand;
	}

	/**
	 * Sets the ThermalCommands of the DeviceSimulator object. ThermalCommands is 
	 * Enum of Strings that is holding all the comrades handled by Thermal module and 
	 * issued by other module(s)
	 * @param ThermalCommands
	 */
	public void setThermalCommand(ThermalCommands thermalCommand) {
		this.thermalCommand = thermalCommand;
	}

	/**
	 * Returns the object of model class CommandData. 
	 * @return CommandData
	 */
	public CommandData getCommand() {
		return command;
	}

	/**
	 * Sets the object of model class CommandData. 
	 * @return CommandData
	 */
	public void setCommand(CommandData command) {
		this.command = command;
	}

	/**
	 * Returns the waiting time of the DeviceSimulator object. By default waiting is 300 millisecond 
	 * @return int
	 */
	public int getWaitTime() {
		return waitTime;
	}

	/**
	 * Sets the waiting time of the DeviceSimulator object. By default waiting is 300 millisecond 
	 * @param int
	 */
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}
	
	public int getToggle() {
		return toggle;
	}

	public void setToggle(int toggle) {
		this.toggle = toggle;
	}
	
	/**
	 * Changes the Status of the DeviceSimulator to OFF after all jobs has been completed
	 * successfully on a particular module or device.
	 */
	public void shutDown(){
		this.state = State.OFF;
	}
	
	/**
	 * Changes the Status of the DeviceSimulator to ON for starting the job on the device or module
	 */
	public void startUp(){
		this.state = State.ON;
	}
	
	/**
	 * Returns ThermalCommands object randomly. There are 3 types of ThermalCommands.<br>
	 * The purpose of this method is to emulate the commands receiving from other module/client
	 * @return ThermalCommands String 
	 */
	public ThermalCommands getRandomThermalCommand()
	{
		if(toggle == 0)
			return ThermalCommands.CURRENT_TEMPERATURE;
		else if(toggle == -1)
			return ThermalCommands.OUTSIDE_TEMPERATURE;
		else 
			return ThermalCommands.CURRENT_TEMPERATURES;
	}

	/**
	 * Overriding the run() of the java.lang for multithreading purpose. <br>
	 * Here is creating ObjectOutputStream and ObjectInputStream for creating new message 
	 * and passing it to the Server and reading incoming message from the server. The communication
	 * are don in String object and formated in JSON.
	 */
	@Override
	public void run() {
		try {
			ObjectOutputStream oos = null;
			ObjectInputStream ois = null;
			Thread.sleep(waitTime);
			// while the rover is ON the sensors work

			while (state == State.ON) 
			{
			  thermalCommand = getRandomThermalCommand();				
				oos = new ObjectOutputStream(getRoverSocket().getNewSocket()
						.getOutputStream());
				
				//Message sent to the server.
				command = new CommandData(id.toString(), thermalCommand.toString());
				String data = command.jsonify();
				oos.writeObject(data);
				
				//message coming from Server
				ois = new ObjectInputStream(this.getRoverSocket().getSocket().getInputStream());
				System.out.println("Data From Server To " + id.toString() + 
					", Respose Of "+ thermalCommand.toString() +": "+ (String) ois.readObject());
				
				oos.close();
				Thread.sleep(waitTime);
			}	
			closeAll();
		}

		catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (Exception error) {
			error.printStackTrace();
		}
	}
	
}