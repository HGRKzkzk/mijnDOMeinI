package model;

import interfaces.Nameable;
import interfaces.Switchable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Cluster implements Serializable, Nameable, Switchable {

	private String name;
	private List<Device> devicesInCLuster = new ArrayList<Device>();
	private boolean switchedOn;
	private boolean activated;

	public Cluster(String name) {

		changeName(name);
		this.switchedOn = requestCurrentValue();

	}

	public void addDeviceToCluster(Device device) {

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
	public boolean requestCurrentValue() {

		int switchedonCount = 0;

		for (Device device : devicesInCLuster) {
			if (device.getSwitchedOn())
				switchedonCount++;
		}

		if (switchedonCount > (devicesInCLuster.size() / 2))
			return true; // true als tenminste de helft van de apparaten in de cluster aan staat

		return false;

	}

}
