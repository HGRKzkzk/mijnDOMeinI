package model;

import java.io.Serializable;

import interfaces.Nameable;
import interfaces.PortHandler;

@SuppressWarnings("serial")
public abstract class Device implements Nameable, PortHandler, Serializable {

	private DeviceCommunicator dCom;
	private String name;
	private int port;
	private boolean switchedOn;
	private boolean activated;
	
	int id;
	

 
	public Device(String name, int port) {

		setdCom(new DeviceCommunicator());
		changePort(port);
		changeName(name);
		switchedOn = true;
		activated = true;
		
		generateAndSetID();

	}

	public Device(String name, int port, boolean on, boolean active) {

		setdCom(new DeviceCommunicator());
		changePort(port);
		changeName(name);
		this.switchedOn = on;
		this.activated = true; // hier gebeuren ander onverklaarbare dingen..?
		generateAndSetID();

	}
	
	public  void generateAndSetID() {
		//TODO ID generator bouwen		
		this.id = 1;
 	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public boolean changePort(int port) {

		if (validatePort(port)) {
			this.port = port;
			return true;
		}
		return false;

	}

	public boolean validatePort(int port) {
		if (port < maxPort && port > minPort)
			return true;
		return false;

	}

	public void changeName(String name) {

		if (validateName(name)) {
			this.name = name;

		} else

		if (name.equals("") || name.isEmpty()) {
			this.name = standardName;
		}

		return;

	}

	public boolean validateName(String name) {

		if (name.length() <= maxNamelength && name.length() > 0)
			return true;
		return false;
	}

	public String getName() {

		return this.name;

	}

	public int getPort() {

		return this.port;

	}
	

	public void requestCurrentValue() {
		getdCom().requeststatus(this);
		return;
	}

	
	

	public void switchOn() {
		this.switchedOn = true;
		getdCom().flipswitch(this);

	}

	public void switchOff() {
		this.switchedOn = false;
		getdCom().flipswitch(this);

	}

	public void setSwitchedOn(boolean b) {
		this.switchedOn = b;
		getdCom().flipswitch(this);

	}

	public boolean getSwitchedOn() {

		return this.switchedOn;
	}

	public DeviceCommunicator getdCom() {
		return dCom;
	}

	public void setdCom(DeviceCommunicator dCom) {
		this.dCom = dCom;
	}

}
