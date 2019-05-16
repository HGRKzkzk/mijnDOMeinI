package model;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class AnalogDevice extends Device implements Serializable {

	public AnalogDevice(String name, int port) {
		super(name, port);
		
	}
	
	
	public AnalogDevice(String naam, int pin, boolean switchedon, boolean active) {
		super(naam, pin, switchedon, active);
	}

}
