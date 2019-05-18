package model;

import java.io.IOException;
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
ffggf
	transient private DeviceFactory dfac = new DeviceFactory();
	transient private ProxyOnsDomein proxy;
	transient private GebruikersApplicatie ga;
	transient private String configMsg;
	transient private String requestFromId = "123";
	transient private String requestForId = "456";

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

		try {
			proxy.connectClientToServer(requestFromId);
		} catch (IOException e) {
			System.out.println("Geen verbinding met server.");
			return;

		}
		String response = proxy.sendRequest("getConfig", requestFromId, requestForId, " ");
		String checkedResponse = handleConfigResponse(response);
		String[] tempArray = checkedResponse.split(INTERSECTION);

		if (tempArray[0].contentEquals(EMPTY_RESPONSE) || tempArray[0].contentEquals("message"))
			return;

		ga.getDeviceList().clear();
		getDevicesFromString(tempArray);
		proxy.closeConnection();
	}

	public String handleConfigResponse(String response) {
		String checkedResponse;
		System.out.println("Response: " + response);
		response = response.replace(STR_START, "");
		response = response.replace(STR_STOP, "");
		System.out.println(response);
		checkedResponse = response;

		return checkedResponse;

	}

	public void getDevicesFromString(String[] tempArray) {

		for (String s : tempArray) {
			String[] tempArray2 = s.split(SPACER);

			Device d = dfac.getDevice(tempArray2);
			ga.getDeviceList().add(d);

		}

	}

	public void pushConfigToServer() {

//		System.out.println("Config van alle aangesloten apparaten verzenden.");
//		System.out.println("-> server");

		ArrayList<Device> devices = (ArrayList<Device>) ga.getDeviceList();
		configMsg = "[" + generateConfigProtocolFromList(devices) + "]";
		System.out.println(configMsg);

		try {
			proxy.connectClientToServer(requestFromId);
		} catch (IOException e) {
			System.out.println("Geen verbinding met server.");
			return;

		}

		String response = proxy.sendRequest("setConfig", requestFromId, requestForId, configMsg);
		System.out.println("Response: " + response);
		proxy.closeConnection();
	}

	public String generateConfigProtocolFromList(ArrayList<Device> devices) {
		String result = "";
		for (Device d : devices) {

			String type = d.getClass().getSimpleName();
			String name = d.getName();
			int port = d.getPort();
			boolean isOn = d.getSwitchedOn();
			boolean isActive = d.isActivated();
			result += MSG_START + type + SPACER + name + SPACER + port + SPACER + isOn + SPACER + isActive + MSG_STOP;

		}

		return result;

	}

	public void flipswitch(Device device) {

//		System.out.println("Schakelaar omzetten.");

		int whichValue = device.getSwitchedOn() ? 1 : 0; // van boolean naar int tbv protocol
		int whichPin = device.getPort();
		int whichAction = ArduinoRequests.switchPin.num;

		int[] message = new int[] { whichPin, whichAction, whichValue };

		sendmessage("setHc", message);
		return;

	}

	public void altervalue(Device device) {

//		System.out.println("Waarde aanpassen.");

		int whichValue = ((Dimmable) device).getDimValue();
		int whichPin = device.getPort();
		int whichAction = ArduinoRequests.setValue.num;

		int[] message = new int[] { whichPin, whichAction, whichValue };

		sendmessage("setHc", message);
		return;
	}

	public void requeststatus(Device device) {

//		System.out.println("Status opvragen.");

		int whichValue = device.getSwitchedOn() ? 1 : 0; // van boolean naar int tbv protocol
		int whichPin = device.getPort();
		int whichAction = ArduinoRequests.getStatus.num;

		int[] message = new int[] { whichPin, whichAction, whichValue };

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

		try {
			proxy.connectClientToServer(requestFromId);
		} catch (IOException e) {
			System.out.println("Geen verbinding met server.");
			return;

		}
		System.out.println("-> server");
		String response = proxy.sendRequest(action, requestFromId, requestForId, msg);
		System.out.println("Response: " + response);
		proxy.closeConnection();

		return;

	}

}
