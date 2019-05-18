package model;

import interfaces.Nameable;
import interfaces.PortHandler;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class Device implements Nameable, PortHandler, Serializable {

	protected DeviceCommunicator dCom;
	private String name;
	private int port;
	protected boolean switchedOn;
	protected boolean activated;

	public Device(String name, int port) {

		dCom = new DeviceCommunicator();
		changePort(port);
		changeName(name);
		switchedOn = true;
		activated = true;

	}

	public Device(String name, int port, boolean on, boolean active) {

		dCom = new DeviceCommunicator();
		changePort(port);
		changeName(name);
		switchedOn = on;
		System.out.println(active);
		activated = active;

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

}
