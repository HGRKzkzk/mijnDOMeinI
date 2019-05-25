package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import interfaces.ArduinoProtocol;
import interfaces.ConfigProtocol;
import interfaces.Dimmable;
import proxy.ProxyOnsDomein;

@SuppressWarnings("serial")
public class DeviceCommunicator implements Serializable, ArduinoProtocol, ConfigProtocol {

	transient private DeviceFactory dfac = new DeviceFactory();
	transient private ProxyOnsDomein proxy;
	transient private GebruikersApplicatie ga;

	private String configMsg;
	private String requestFromId = "123";
	private String requestForId = "5678";

	public DeviceCommunicator() {
		this.proxy = new ProxyOnsDomein();
	}

	public DeviceCommunicator(GebruikersApplicatie gebruikersApplicatie) {
		this.ga = gebruikersApplicatie;
		this.proxy = new ProxyOnsDomein();
	}

	public void setProxy() {
		this.proxy = new ProxyOnsDomein();
	}



	public void requestConfigFromServer() {

		String uncheckedResponse = toServer("getConfig", " ");
		String checkedResponse = handleConfigResponse(uncheckedResponse); // er iets moois van maken
		String[] tempArray = checkedResponse.split(INTERSECTION); // in hapklare brokken verdelen.
		if (!isValidResponse(tempArray))
			return;

		ga.getDeviceList().clear(); // lijst van devices leegmaken
		getDevicesFromString(tempArray);

	}

	private boolean isValidResponse(String[] tempArray) {

		if (tempArray[0].contentEquals(EMPTY_RESPONSE) || tempArray[0].contentEquals("message")
				|| tempArray[0].contentEquals("null"))
			return false;

		return true;

	}

	private String handleConfigResponse(String response) {
		response = response.replace(STR_START, "");
		response = response.replace(STR_STOP, "");

		if (response.equals(null)) { // TODO betere check maken
			response = "message";
		}

		return response;

	}

	private void getDevicesFromString(String[] tempArray) {

		for (String s : tempArray) {
			String[] tempArray2 = s.split(SPACER);

			Device d = dfac.getDevice(tempArray2);
			ga.getDeviceList().add(d);

		}

	}

	public boolean pushConfigToServer() {
		ArrayList<Device> devices = (ArrayList<Device>) ga.getDeviceList(); // lijst van devices vanuit
		// GebruikersApplicatie ophalen
		configMsg = OBJECT_START + generateConfigProtocolFromList(devices) + OBJECT_END; // er een string van maken
		String response = toServer("setConfig", configMsg);
		return response.equals("setConfigOK"); // true bij gewenste / verwachte response, anders false.
	}



	private String generateConfigProtocolFromList(ArrayList<Device> devices) {
		StringBuilder result = new StringBuilder();
		for (Device d : devices) {
			// long id = d.getID();
			String type = d.getClass().getSimpleName();
			String name = d.getName();
			int port = d.getPort();
			boolean isOn = d.getSwitchedOn();
			boolean isActive = d.isActivated();
			result.append(MSG_START).append(type).append(SPACER).append(name).append(SPACER).append(port).append(SPACER)
					.append(isOn).append(SPACER).append(isActive).append(MSG_STOP);

		}

		return result.toString();

	}

	public boolean DeviceAction(Device device, String action){
		String cmd = "setHc";
		int whichPin = device.getPort();
		int whichAction = 0;
		int whichValue = 0;

		if(action.equals("switch")){
			whichAction = ArduinoRequests.switchPin.num;
			whichValue = device.getSwitchedOn() ? 0 : 1; // van boolean naar int tbv protocol
		}

		if (action.equals("alter")){
			whichAction = ArduinoRequests.setValue.num;
			whichValue = ((Dimmable) device).getDimValue();
		}

		if(action.equals("read")){
			cmd = "getHc";
			whichAction = ArduinoRequests.getStatus.num;
		}

		int[] message = new int[] { whichPin, whichAction, whichValue };
		return sendmessage(cmd, message);

	}


	private boolean sendmessage(String action, int[] message) {
		int whichPin = message[PIN];
		int whichAction = message[ACTION];
		int whichValue = message[VALUE];

		String msg = ARD_BOM + whichPin + ARD_DIVIDER + whichAction + ARD_DIVIDER + whichValue + ARD_EOM;

		String response = toServer(action, msg);

		if (response.equals("No connection with HC")) {
			return false;
		}

		return true;
	}

	private String toServer(String cmd, String msg) {
		connectToServer(requestFromId);
		String resp = proxy.sendRequest(cmd, requestFromId, requestForId, msg); // naar server sturen
		proxy.closeConnection();
		return resp;

	}

	private void connectToServer(String requestFromId) {

		try {

			if(proxy == null){
				this.proxy = new ProxyOnsDomein();
			}
			proxy.connectClientToServer(requestFromId);
		} catch (IOException e) {
			System.out.println("Geen verbinding met server.");
		}

	}

}
