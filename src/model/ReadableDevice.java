package model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ReadableDevice extends Device implements Serializable {

	public ReadableDevice(String name, int port) {
		super(name, port);
		
	}

	@Override
	public boolean getSwitchedOn() {
		 
		return false;
	}

	@Override
	public void setSwitchedOn(boolean b) {
		 
		
	}

	@Override
	protected void switchOn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void switchOff() {
		// TODO Auto-generated method stub
		
	}
	
	

}
