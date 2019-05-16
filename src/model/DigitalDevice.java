package model;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class DigitalDevice extends Device implements Serializable{

	public DigitalDevice(String name, int port) {
		super(name, port);
		
	}

	public DigitalDevice(String naam, int pin, boolean switchedon, boolean active) {
		super(naam, pin, switchedon, active);
	}

}
