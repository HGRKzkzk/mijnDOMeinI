package model;

import java.io.Serializable;

import interfaces.Dimmable;

@SuppressWarnings("serial")
public class DimmableDevice extends DigitalDevice implements Dimmable, Serializable {

	private int dimValue = 100;

	public DimmableDevice(String name, int port) {
		super(name, port);

	}

	public DimmableDevice(String naam, int pin, boolean switchedOn, boolean active) {
		super(naam, pin, switchedOn, active);

	}

	
	public void setDimValue(int newValue) {


		if(super.getdCom().DeviceAction(this, "alter"))
		{
			this.dimValue = newValue;
		}

	}

 

	public int getDimValue() {
		return this.dimValue;

	}


}
