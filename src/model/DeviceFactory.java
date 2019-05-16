package model;

public class DeviceFactory {

	public Device getDevice(String devicetype, String name, int port, boolean analoog) {

		if (devicetype == DeviceTypes.READONLY.getDescription()) {
			return new ReadableDevice(name, port);

		} else if (devicetype == DeviceTypes.SWITCHABLE.getDescription()) {
			return new SwitchableDevice(name, port);

		} else if (devicetype == DeviceTypes.DIMMABLE.getDescription()) {
			return new DimmableDevice(name, port);

		}

		return new ReadableDevice(name, port);

	}

	public Device getDevice(String[] tempArray2) {
		
		

		String type = tempArray2[0];
		String naam = tempArray2[1];
		int pin = Integer.parseInt(tempArray2[2]);
		boolean switchedon = tempArray2[3].toString().equals("true") ? true : false;
		boolean active = tempArray2[4].toString().equals("true") ? true : false;
		
		System.out.println("Type: " + type);
		System.out.println("Naam: " + naam);
		System.out.println("Pin: " + pin);
		System.out.println("ON: " + switchedon);
		System.out.println("ACTIVE: " + active);

		if (type.equals("DimmableDevice")) {
			
			return new DimmableDevice(naam, pin, switchedon, active);
		}

		else if (type.equals("SwitchableDevice")) {
			return new SwitchableDevice(naam, pin, switchedon,active);
		}

		return new ReadableDevice(naam, pin);

	}

}
