package model;

import java.io.Serializable;

import interfaces.Nameable;
import interfaces.PortHandler;

@SuppressWarnings("serial")
public abstract class Device implements Nameable, PortHandler, Serializable {

	private DeviceCommunicator dCom;
	private String name;
	private long id;
	private int port;
	private boolean switchedOn;
	private boolean activated;

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

	public void generateAndSetID() {

		long j = System.currentTimeMillis();
		// String k = Long.toString(j);
		// TODO ID generator bouwen
		this.id = j;
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
		getdCom().requeststatus(this);
	}

	public void switchOn() {

		if (getdCom().flipswitch(this)) {
			this.switchedOn = true;
		}

	}

	public void switchOff() {

		if (getdCom().flipswitch(this)) {
			this.switchedOn = false;
		}

	}

	public void setSwitchedOn(boolean b) {

		if (getdCom().flipswitch(this)) {
			this.switchedOn = b;
		}

	}

	public boolean getSwitchedOn() {

		return this.switchedOn;
	}

	public DeviceCommunicator getdCom() {
		return dCom;
	}

	public long getID() {
		return this.id;
	}

	public void setdCom(DeviceCommunicator dCom) {
		this.dCom = dCom;
	}

}
