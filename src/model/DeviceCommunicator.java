package model;

import java.io.Serializable;
import java.util.ArrayList;

import interfaces.ArduinoConventions;
import interfaces.ConfigProtocol;
import interfaces.Dimmable;
import jserial.ArduinoRequests;
import jserial.jSerialcomm;
import proxy.ProxyOnsDomein;
import view.Main;

@SuppressWarnings("serial")
public class DeviceCommunicator implements Serializable, ArduinoConventions, ConfigProtocol {

	transient private DeviceFactory dfac = new DeviceFactory();
	transient private ProxyOnsDomein proxy;
	transient private GebruikersApplicatie ga;
	transient private String configMsg;

	public DeviceCommunicator() {
		proxy = new ProxyOnsDomein();
	}

	public DeviceCommunicator(GebruikersApplicatie gebruikersApplicatie) {
		this.ga = gebruikersApplicatie;
		proxy = new ProxyOnsDomein();
	}

	public void requestConfigFromServer() {

		System.out.println("Config van alle aangesloten apparaten opvragen.");
		System.out.println("-> server");

		if (proxy.connectClientToServer()) {
			String response;
			response = proxy.sendRequest("getConfig", " ");
			System.out.println("Response: " + response);

			response = response.replace(STR_START, "");
			response = response.replace(STR_STOP, "");
			System.out.println(response);
			String[] tempArray;
			tempArray = response.split(INTERSECTION);

			if (tempArray[0].contentEquals(EMPTY_RESPONSE) || tempArray[0].contentEquals("message"))
				return;

			ga.getDeviceList().clear();

			for (String s : tempArray) {
				String[] tempArray2 = s.split(SPACER);

				Device d = dfac.getDevice(tempArray2);
				ga.getDeviceList().add(d);

			}
			proxy.closeConnection();
		}

		return;

	}

	public void pushConfigToServer() {

//		System.out.println("Config van alle aangesloten apparaten verzenden.");
//		System.out.println("-> server");

		ArrayList<Device> devices = (ArrayList<Device>) ga.getDeviceList();
		configMsg = "[";

		// TYPE _ NAME _ PORT _ ON _ ACTIVE
		for (Device d : devices) {

			String type = d.getClass().getSimpleName();
			String name = d.getName();
			int port = d.getPort();
			boolean isOn = d.getSwitchedOn();
			boolean isActive = d.isActivated();
			configMsg += MSG_START + type + SPACER + name + SPACER + port + SPACER + isOn + SPACER + isActive
					+ MSG_STOP;

		}

		configMsg += "]";

		if (proxy.connectClientToServer()) {
			String response = proxy.sendRequest("setConfig", configMsg);
			System.out.println("Response: " + response);
			proxy.closeConnection();
		}

		return;

	}

	public void flipswitch(Device device) {

//		System.out.println("Schakelaar omzetten.");

		int[] message = new int[3];
		int whichValue = device.getSwitchedOn() ? 1 : 0; // van boolean naar int tbv protocol
		int whichPin = device.getPort();
		int whichAction = ArduinoRequests.switchPin.num;

		message[0] = whichPin;
		message[1] = whichAction;
		message[2] = whichValue;

		sendmessage("setHc", message);
		return;

	}

	public void altervalue(Device device) {

//		System.out.println("Waarde aanpassen.");

		int pinValue = ((Dimmable) device).getDimValue();
		int[] message = new int[3];
		int whichValue = pinValue;
		int whichPin = device.getPort();
		int whichAction = ArduinoRequests.setValue.num;

		message[0] = whichPin;
		message[1] = whichAction;
		message[2] = whichValue;

		sendmessage("setHc", message);
		return;
	}

	public void requeststatus(Device device) {

//		System.out.println("Status opvragen.");

		int[] message = new int[3];
		int whichValue = device.getSwitchedOn() ? 1 : 0; // van boolean naar int tbv protocol
		int whichPin = device.getPort();
		int whichAction = ArduinoRequests.getStatus.num;

		message[0] = whichPin;
		message[1] = whichAction;
		message[2] = whichValue;

		sendmessage("getHc", message);
		return;
	}

	public void sendmessage(String action, int[] message) {

		int whichPin = message[0];
		int whichAction = message[1];
		int whichValue = message[2];
		String msg = ARD_BOM + whichPin + ARD_DIVIDER + whichAction + ARD_DIVIDER + whichValue + ARD_EOM;

		// System.out.println(msg);

		if (Main.getGa().isDirectToArduino()) {
			System.out.println("-> arduino");
			jSerialcomm.sendProtocolData(whichPin, whichAction, whichValue);
			return;

		}

		if (proxy.connectClientToServer()) {
			System.out.println("-> server");
			String response = proxy.sendRequest(action, msg);
			System.out.println("Response: " + response);
			proxy.closeConnection();
		}

		return;

	}

}
