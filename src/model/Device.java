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

	public void generateAndSetID() {
		// TODO ID generator bouwen
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
		return (port < maxPort && port > minPort);

	}

	public void changeName(String name) {

		if (validateName(name)) {
			this.name = name;

		} else

		if (name.equals("") || name.isEmpty()) {
			this.name = standardName;
		}

		/* Deze return doet niks, wellicht changeName ook een boolean laten retouneren net zoals changePort.
		Als ie nu hier komt omdat validateName false retourneert dan wordt dit niet goed verwerkt.
		 */
		return;

	}

	public boolean validateName(String name) {
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

	public void setdCom(DeviceCommunicator dCom) {
		this.dCom = dCom;
	}

}
