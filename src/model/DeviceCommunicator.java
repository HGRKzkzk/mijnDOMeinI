package model;

import interfaces.Dimmable;
import jserial.ArduinoRequests;
import jserial.jSerialcomm;
import proxy.ProxyOnsDomein;
import view.Main;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class DeviceCommunicator implements Serializable {

	transient DeviceFactory dfac = new DeviceFactory();
	transient ProxyOnsDomein proxy;
	transient GebruikersApplicatie ga;
		
	transient String configMsg;
	transient final String SPACER = "_";
	transient final String INTERSECTION = ">><<";
	transient final String STR_START = "[<<";
	transient final String STR_STOP = ">>]";
	transient final String MSG_START = "<<";
	transient final String MSG_STOP = ">>";
	transient final String EMPTY_RESPONSE = "[]";

	transient final String ARD_DIVDER = ":";
	transient final String ARD_BOM = "<";
	transient final String ARD_EOM = ">";

	public DeviceCommunicator() {
		proxy = new ProxyOnsDomein();
	}

	public DeviceCommunicator(GebruikersApplicatie gebruikersApplicatie) {
		this.ga = gebruikersApplicatie;
	}

	public void requestConfigFromServer() {

		System.out.println("Status van alle aangesloten apparaten opvragen.");
		System.out.println("-> server");
		String response;

		if (proxy == null) {
			proxy = new ProxyOnsDomein();
		}

		if (proxy.connectClientToServer()) {

			response = proxy.sendRequest("getConfig", " ");
			System.out.println("Response: " + response);

			response = response.replace(INTERSECTION, " ");
			response = response.replace(STR_START, "");
			response = response.replace("STR_STOP", "");
			System.out.println(response);
			String[] tempArray;
			tempArray = response.split(" ");
			// System.out.println(tempArray.length + " apparaten gevonden.");

			ga.getDeviceList().clear();

			for (String s : tempArray) {
				String[] tempArray2 = s.split(SPACER);
				if (tempArray[0].contentEquals(EMPTY_RESPONSE) || tempArray[0].contentEquals("message"))
					return;
				Device d = dfac.getDevice(tempArray2);
				ga.getDeviceList().add(d);

			}

		}

		proxy.closeConnection();
		return;

	}

	public void pushConfigToServer() {

		configMsg = "[";
		System.out.println("Status van alle aangesloten apparaten verzenden.");
		System.out.println("-> server");

		ArrayList<Device> devices = (ArrayList<Device>) ga.getDeviceList();

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

		String response;
		if (proxy == null) {
			proxy = new ProxyOnsDomein();
		}

		if (proxy.connectClientToServer()) {

			response = proxy.sendRequest("setConfig", configMsg);
			System.out.println("Response: " + response);

		}

		proxy.closeConnection();
		return;

	}

	public void flipswitch(Device device) {

		System.out.println("Schakelaar omzetten.");

		int[] message = new int[3];
		int whichValue = device.getSwitchedOn() ? 1 : 0; // van boolean naar int tbv protocol
		int whichPin = device.getPort();
		int whichAction = ArduinoRequests.switchPin.num;

		message[0] = whichPin;
		message[1] = whichAction;
		message[2] = whichValue;

		sendmessage("setHc", message);

	}

	public void altervalue(Device device) {

		System.out.println("Waarde aanpassen.");

		int pinValue = ((Dimmable) device).getDimValue();
		int[] message = new int[3];
		int whichValue = pinValue;
		int whichPin = device.getPort();
		int whichAction = ArduinoRequests.setValue.num;

		message[0] = whichPin;
		message[1] = whichAction;
		message[2] = whichValue;

		sendmessage("setHc", message);

	}

	public void requeststatus(Device device) {

		System.out.println("Status opvragen.");

		int[] message = new int[3];
		int whichValue = device.getSwitchedOn() ? 1 : 0; // van boolean naar int tbv protocol
		int whichPin = device.getPort();
		int whichAction = ArduinoRequests.getStatus.num;

		message[0] = whichPin;
		message[1] = whichAction;
		message[2] = whichValue;

		sendmessage("getHc", message);

	}

	public void sendmessage(String action, int[] message) {

		int whichPin = message[0];
		int whichAction = message[1];
		int whichValue = message[2];
		String msg = ARD_BOM + whichPin + ARD_DIVDER + whichAction + ARD_DIVDER + whichValue + ARD_EOM;

		// System.out.println(msg);

		if (Main.getGa().isDirectToArduino()) {
			System.out.println("-> arduino");
			jSerialcomm.sendProtocolData(whichPin, whichAction, whichValue);
			return;

		}

		System.out.println("-> server");
		String response;
		if (proxy == null) {
			proxy = new ProxyOnsDomein();
		}

		if (proxy.connectClientToServer()) {

			response = proxy.sendRequest(action, msg);
			System.out.println("Response: " + response);

		}

		proxy.closeConnection();

		return;

	}

}
