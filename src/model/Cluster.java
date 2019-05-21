package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import interfaces.Nameable;
import interfaces.Switchable;

@SuppressWarnings("serial")
public class Cluster implements Serializable, Nameable, Switchable {

	private String name;
	private List<Device> devicesInCluster = new ArrayList<>();
	private boolean switchedOn;
	private boolean activated;

	public Cluster(String name) {

		changeName(name);
		

	}

	public void addDeviceToCluster(Device device) {
		this.devicesInCluster.add(device);
	}

	public void removeDeviceFromCluster(Device device) {
		this.devicesInCluster.remove(device);
	}

	public void switchOn() {
		System.out.println("Cluster aanzetten");
	
		for (Device device : devicesInCluster) {
			if (device.isActivated())
				device.switchOn();

		}

		this.switchedOn = true;

	}

	public void switchOff() {
		System.out.println("Cluster uitzetten");
		

		for (Device device : devicesInCluster) {
			if (device.isActivated())
				device.switchOff();

		}
		this.switchedOn = false;

	}

	public String giveClusterContentsAsString() {
		String str = "";
		for (Device device : devicesInCluster) {
			str += device.getName() + ", ";
		}

		return str.substring(0, str.length() - 2);
	}

	public List<Device> getDevicesInCluster() {
		return devicesInCluster;
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

		// Zelfde opmerking van toepassing als bij Device
		return;

	}

	public boolean validateName(String name) {

		return (name.length() <= maxNamelength && name.length() > 0 && !name.isEmpty());
	}
	

	@Override
	public void requestCurrentValue() {

		int switchedonCount = 0;

		for (Device device : devicesInCluster) {
			if (((SwitchableDevice) device).getSwitchedOn())
				switchedonCount++;
		}

		if (switchedonCount > (devicesInCluster.size() / 2))
			return; // true als tenminste de helft van de apparaten in de cluster aan staat



	}

	@Override
	public boolean getSwitchedOn() {
		// TODO Auto-generated method stub
		return false;
	}

}
