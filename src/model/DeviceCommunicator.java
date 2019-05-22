package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import interfaces.ArduinoConventions;
import interfaces.ConfigProtocol;
import interfaces.Dimmable;
import proxy.ProxyOnsDomein;

@SuppressWarnings("serial")
public class DeviceCommunicator implements Serializable, ArduinoConventions, ConfigProtocol {

	transient private DeviceFactory dfac = new DeviceFactory();
	transient private ProxyOnsDomein proxy;
	transient private GebruikersApplicatie ga;
	transient private String configMsg;
	transient private String requestFromId = "123";
	transient private String requestForId = "5678";

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

		String uncheckedResponse = proxy.sendRequest("getConfig", requestFromId, requestForId, " ");
		String checkedResponse = handleConfigResponse(uncheckedResponse);
		String[] tempArray = checkedResponse.split(INTERSECTION);

		if (tempArray[0].contentEquals(EMPTY_RESPONSE) || tempArray[0].contentEquals("message")
				|| tempArray[0].contentEquals("null"))
			return;

		ga.getDeviceList().clear();
		getDevicesFromString(tempArray);
		proxy.closeConnection();
	}

	public String handleConfigResponse(String response) {

		System.out.println("Response: " + response);
		response = response.replace(STR_START, "");
		response = response.replace(STR_STOP, "");
		System.out.println(response);
		if (response.equals(null)) {
			response = "message";
		}

		return response;

	}

	public void getDevicesFromString(String[] tempArray) {

		for (String s : tempArray) {
			String[] tempArray2 = s.split(SPACER);

			Device d = dfac.getDevice(tempArray2);
			ga.getDeviceList().add(d);

		}

	}

	public boolean pushConfigToServer() {

//		System.out.println("Config van alle aangesloten apparaten verzenden.");
//		System.out.println("-> server");

		ArrayList<Device> devices = (ArrayList<Device>) ga.getDeviceList();
		configMsg = OBJECT_START + generateConfigProtocolFromList(devices) + OBJECT_END;
		System.out.println(configMsg);

		try {
			proxy.connectClientToServer(requestFromId);
		} catch (IOException e) {
			System.out.println("Geen verbinding met server.");
			return false;

		}

		String response = proxy.sendRequest("setConfig", requestFromId, requestForId, configMsg);
		System.out.println("Response: " + response);
		proxy.closeConnection();
		return response.equals("setConfigOK");
	}

	public String generateConfigProtocolFromList(ArrayList<Device> devices) {
		String result = "";
		for (Device d : devices) {
			int id = d.id;
			String type = d.getClass().getSimpleName();
			String name = d.getName();
			int port = d.getPort();
			boolean isOn = d.getSwitchedOn();
			boolean isActive = d.isActivated();
			result += MSG_START + type + SPACER + name + SPACER + port + SPACER + isOn + SPACER + isActive + SPACER + id
					+ MSG_STOP;

		}

		return result;

	}

	public void pushStateToServer() {

	}

	public boolean flipswitch(Device device) {

//		System.out.println("Schakelaar omzetten.");

		int whichValue = device.getSwitchedOn() ? 0 : 1; // van boolean naar int tbv protocol
		int whichPin = device.getPort();
		int whichAction = ArduinoRequests.switchPin.num;

		int[] message = new int[] { whichPin, whichAction, whichValue };

		return sendmessage("setHc", message);

	}

	public boolean altervalue(Device device) {

//		System.out.println("Waarde aanpassen.");

		int whichValue = ((Dimmable) device).getDimValue();
		int whichPin = device.getPort();
		int whichAction = ArduinoRequests.setValue.num;

		int[] message = new int[] { whichPin, whichAction, whichValue };

		return sendmessage("setHc", message);

	}

	public boolean requeststatus(Device device) {

//		System.out.println("Status opvragen.");

		int whichPin = device.getPort();
		int whichValue = device.getSwitchedOn() ? 1 : 0; // van boolean naar int tbv protocol
		int whichAction = ArduinoRequests.getStatus.num;

		int[] message = new int[] { whichPin, whichAction, whichValue };

		return sendmessage("getHc", message);

	}

	public boolean sendmessage(String action, int[] message) {
		int whichPin = message[0];
		int whichAction = message[1];
		int whichValue = message[2];

		String msg = ARD_BOM + whichPin + ARD_DIVIDER + whichAction + ARD_DIVIDER + whichValue + ARD_EOM;

		System.out.println(msg);

		try {

			if (proxy.equals(null)) {
				proxy = new ProxyOnsDomein();
			}

			proxy.connectClientToServer(requestFromId);
		} catch (IOException e) {
			System.out.println("Geen verbinding met server.");
			return false;

		}
		System.out.println("-> server");
		String response = proxy.sendRequest(action, requestFromId, requestForId, msg);
		System.out.println("Response: " + response);
		proxy.closeConnection();

		if (response.equals("No connection with HC")) {
			return false;
		}

		return true;
	}

}
