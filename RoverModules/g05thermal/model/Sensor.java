package model;

import generic.RoverClientRunnable;

import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import constant.Modules;
import constant.State;


public class Sensor extends RoverClientRunnable {
	Modules ID;
	public int waitTime = 2000;
	Modules name;
	ModuleBase moduleBase;
	public double currentTemp;
	
	private State state;

	public Sensor(int port, InetAddress host, Modules ID, ModuleBase moduleBase)
			throws UnknownHostException {
		super(port, host);
		startUp();
		this.ID = ID;
		this.moduleBase = moduleBase;
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ObjectOutputStream oos = null;
			Thread.sleep(waitTime);
			// while the rover is ON the sensors work

			while (state == State.ON) {

				currentTemp = randomTemperature(moduleBase.getMinTemp(), moduleBase.getMaxTemp());
				
				oos = new ObjectOutputStream(getRoverSocket().getNewSocket()
						.getOutputStream());
				TemperatureResponse tempResp = new TemperatureResponse(ID.toString(), currentTemp);
				String data = tempResp.jsonify();

				oos.writeObject(data);
				
				oos.close();
				Thread.sleep(waitTime);
			}
			//if(state == State.OFF)
			closeAll();
		} catch (UnknownHostException e) {
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
	
	public double randomTemperature(double min, double max){
		double check = (float)Math.random();
		if (check >= 0 && check < 0.25){
			return (double)Math.random()*(max - min) + min;
		}
		else if (check >= 0.25 && check < 0.75){
			//return a value between min + 10 and min
			return (double)Math.random()*(10) + (min - 10);
		}
		else if (check >= 0.75 && check <= 1){
			//return a value between max and max + 10
			return (double)Math.random()*(10) + (max);
		}
		return 0;
	}
}