package model;

import java.io.Serializable;

import interfaces.Nameable;
import interfaces.PortHandler;

@SuppressWarnings("serial")
public abstract class Device implements Nameable, PortHandler, Serializable {

	private DeviceCommunicator dCom;
	private String name;
	private int port;
	private int value;
	private boolean switchedOn;
	private boolean activated;

	public Device(String name, int port) {

		setdCom(new DeviceCommunicator());
		changePort(port);
		changeName(name);
		switchedOn = true;
		activated = true;



	}

	public Device(String name, int port, boolean on, boolean active) {

		setdCom(new DeviceCommunicator());
		changePort(port);
		changeName(name);
		this.switchedOn = on;
		this.activated = true; // hier gebeuren ander onverklaarbare dingen..?

	}


	public boolean isActivated() {
		return activated;
	}

	public void setValue(int value){
		this.value = value;
		System.out.println(this.value);
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
		return (port <= maxPort && port >= minPort);

	}

	public boolean changeName(String name) {

		if (checkStringLength(name)) {
			this.name = name;
			return true;

		} else

		if (name.equals("") || name.isEmpty()) {
			this.name = standardName;
			return true;
		}

		return false;

	}

	public boolean checkStringLength(String name) {
		return (name.length() <= maxNamelength && name.length() > 0);
	}

	public String getName() {

		return this.name;

	}

	public int getPort() {

		return this.port;

	}

	public void requestCurrentValue() {
		getdCom().DeviceAction(this, "read");
	}

	public void switchOn() {

		if (getdCom().DeviceAction(this, "switch")) {
			this.switchedOn = true;
		}

	}

	public void switchOff() {

		if (getdCom().DeviceAction(this, "switch")) {
			this.switchedOn = false;
		}

	}

	public void setSwitchedOn(boolean b) {

		if (getdCom().DeviceAction(this, "switch")) {
			this.switchedOn = b;
		}

	}

	public boolean getSwitchedOn() {

		return this.switchedOn;
	}

	public DeviceCommunicator getdCom() {
		return dCom;
	}

	private void setdCom(DeviceCommunicator dCom) {
		this.dCom = dCom;
	}

}
