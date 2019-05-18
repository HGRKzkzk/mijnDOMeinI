package model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SwitchableDevice extends DigitalDevice implements Serializable {

	public SwitchableDevice(String name, int port) {
		super(name, port);

	}

	public SwitchableDevice(String naam, int pin, boolean switchedon, boolean active) {
		super(naam, pin, switchedon, active);


	}

	public void setSwitchedOn(boolean b) {
		this.switchedOn = b;
		dCom.flipswitch(this);

	}


	public boolean getSwitchedOn() {

		return this.switchedOn;
	}





}
