package model;

import java.io.Serializable;

import interfaces.Dimmable;

@SuppressWarnings("serial")
public class DimmableDevice extends DigitalDevice implements Dimmable, Serializable {

	private int dimvalue = 100;

	public DimmableDevice(String name, int port) {
		super(name, port);

	}

	public DimmableDevice(String naam, int pin, boolean switchedon, boolean active) {
		super(naam, pin, switchedon, active);

	}

	
	public void setDimValue(int newvalue) {
		
		if(super.getdCom().altervalue(this)) {
			this.dimvalue = newvalue;	
		}

	}

 

	public int getDimValue() {
		return this.dimvalue;

	}


}
