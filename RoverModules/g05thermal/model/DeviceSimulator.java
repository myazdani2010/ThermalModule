package model;

import generic.RoverClientRunnable;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import constant.Modules;
import constant.State;
import constant.ThermalCommands;

public class DeviceSimulator extends RoverClientRunnable {
	private State state;
	Modules id;
	ThermalCommands thermalCommand;
	CommandData command;
	public int waitTime = 3000;
	private int toggle;
	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Modules getId() {
		return id;
	}

	public void setId(Modules id) {
		this.id = id;
	}

	public ThermalCommands getThermalCommand() {
		return thermalCommand;
	}

	public void setThermalCommand(ThermalCommands thermalCommand) {
		this.thermalCommand = thermalCommand;
	}

	public CommandData getCommand() {
		return command;
	}

	public void setCommand(CommandData command) {
		this.command = command;
	}

	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

	public int getToggle() {
		return toggle;
	}

	public void setToggle(int toggle) {
		this.toggle = toggle;
	}

	public DeviceSimulator(int port, InetAddress host, Modules id, int toggle)
			throws UnknownHostException {
		super(port, host);
		this.id = id;
		setToggle(toggle);
		startUp();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ObjectOutputStream oos = null;
			ObjectInputStream ois = null;
			Thread.sleep(waitTime);
			// while the rover is ON the sensors work

			while (state == State.ON) {
				
				thermalCommand = getRandomThermalCommand();				
				oos = new ObjectOutputStream(getRoverSocket().getNewSocket()
						.getOutputStream());
				
				command = new CommandData(id.toString(), thermalCommand.toString());
				String data = command.jsonify();
				oos.writeObject(data);
				
				ois = new ObjectInputStream(this.getRoverSocket().getSocket().getInputStream());
				System.out.println("Data From Server To " + id.toString() + ", Respose Of "+ thermalCommand.toString() +": "+ (String) ois.readObject());
				
				oos.close();
				Thread.sleep(waitTime);
			}	
			closeAll();
		}

		catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (Exception error) {
			System.out.println(": Error:"
					+ error.getMessage());
		}
	}
	
	public void shutDown(){
		this.state = State.OFF;
	}
	
	public void startUp(){
		this.state = State.ON;
	}
	
	public ThermalCommands getRandomThermalCommand(){
		
		ThermalCommands thrmCmd = null;
		if(toggle == 0)
			thrmCmd =  ThermalCommands.CURRENT_TEMPERATURE;
		else if(toggle == -1)
			thrmCmd =  ThermalCommands.OUTSIDE_TEMPERATURE;
		else 
			thrmCmd = ThermalCommands.CURRENT_TEMPERATURES;
		
		return thrmCmd;
		
		
	}

}