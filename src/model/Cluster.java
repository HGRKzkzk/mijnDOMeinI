package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import interfaces.Nameable;
import interfaces.Switchable;

@SuppressWarnings("serial")
public class Cluster implements Serializable, Nameable, Switchable {

	private String name;
	private List<Device> devicesInCLuster = new ArrayList<Device>();
	private boolean switchedOn;
	private boolean activated;

	public Cluster(String name) {

		changeName(name);
		

	}

	public void addDeviceToCluser(Device device) {
		this.devicesInCLuster.add(device);
	}

	public void removeDeviceFromCluser(Device device) {
		this.devicesInCLuster.remove(device);
	}

	public void switchOn() {
		System.out.println("CLuster aanzetten");
	
		for (Device device : devicesInCLuster) {
			if (device.isActivated())
				device.switchOn();

		}

		this.switchedOn = true;

	}

	public void switchOff() {
		System.out.println("CLuster uitzetten");
		

		for (Device device : devicesInCLuster) {
			if (device.isActivated())
				device.switchOff();

		}
		this.switchedOn = false;

	}

	public String giveClusterContentsAsString() {
		String str = "";
		for (Device device : devicesInCLuster) {
			str += device.getName() + ", ";
		}
		String contents = str.substring(0, str.length() - 2);
		return contents;

	}

	public List<Device> getDevicesInCLuster() {
		return devicesInCLuster;
	}

	public String getName() {
		return name;
	}

	public boolean isSwitchedOn() {
		return switchedOn;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setSwitchedOn(boolean switchedOn) {
		this.switchedOn = switchedOn;
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

		if (name.length() <= maxNamelength && name.length() > 0 && !name.isEmpty())
			return true;
		return false;
	}
	

	@Override
	public void requestCurrentValue() {

		int switchedonCount = 0;

		for (Device device : devicesInCLuster) {
			if (((SwitchableDevice) device).getSwitchedOn())
				switchedonCount++;
		}

		if (switchedonCount > (devicesInCLuster.size() / 2))
			return; // true als tenminste de helft van de apparaten in de cluster aan staat

		

	}

	@Override
	public boolean getSwitchedOn() {
		// TODO Auto-generated method stub
		return false;
	}

}
