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
		proxy = new ProxyOnsDomein();
	}

	public DeviceCommunicator(GebruikersApplicatie gebruikersApplicatie) {
		this.ga = gebruikersApplicatie;
		proxy = new ProxyOnsDomein();
	}



	public void requestConfigFromServer() {

//		System.out.println("Config van alle aangesloten apparaten opvragen.");
//		System.out.println("-> server");

		try { // try 
			proxy.connectClientToServer(requestFromId);
			
		} catch (IOException e) {
			System.out.println("Geen verbinding met server.");
			return; // or die.. als er geen verbinding is kunnen we ook niks ophalen dus stopt deze methode. 

		}
		String uncheckedResponse = proxy.sendRequest("getConfig", requestFromId, requestForId, " "); //string met configuratie van server ophalen.
		proxy.closeConnection(); //verbinding met server dichtgooien. 
		 
		
		
		String checkedResponse = handleConfigResponse(uncheckedResponse); //er iets moois van maken 
		String[] tempArray = checkedResponse.split(INTERSECTION); // in hapklare brokken verdelen. 

		if (tempArray[0].contentEquals(EMPTY_RESPONSE) || tempArray[0].contentEquals("message")
				|| tempArray[0].contentEquals("null"))
			return; // als er een ongeldige config binnenkomt stopt de methode. 

		ga.getDeviceList().clear(); //lijst van devices leegmaken 
		getDevicesFromString(tempArray);
		
	}

	private String handleConfigResponse(String response) {

		System.out.println("Response: " + response);
		response = response.replace(STR_START, "");
		response = response.replace(STR_STOP, "");
		System.out.println(response);
		
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

//		System.out.println("Config van alle aangesloten apparaten verzenden.");
//		System.out.println("-> server");

		ArrayList<Device> devices = (ArrayList<Device>) ga.getDeviceList(); //lijst van devices vanuit GebruikersApplicatie ophalen
		configMsg = OBJECT_START + generateConfigProtocolFromList(devices) + OBJECT_END; //er een string van maken
		System.out.println(configMsg);

		try {
			proxy.connectClientToServer(requestFromId);
		} catch (IOException e) {
			System.out.println("Geen verbinding met server.");
			return false;

		}

		String response = proxy.sendRequest("setConfig", requestFromId, requestForId, configMsg); //naar server sturen
		proxy.closeConnection();
		System.out.println("Response: " + response);
		
		return response.equals("setConfigOK"); // true bij gewenste / verwachte response, anders false. 
	}

	private String generateConfigProtocolFromList(ArrayList<Device> devices) {
		String result = "";
		for (Device d : devices) {
			long id = d.getID();
			String type = d.getClass().getSimpleName();
			String name = d.getName();
			int port = d.getPort();
			boolean isOn = d.getSwitchedOn();
			boolean isActive = d.isActivated();
			result += MSG_START + type + SPACER + name + SPACER + port + SPACER + isOn + SPACER + isActive + // SPACER + id +  
					MSG_STOP;

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

	private boolean sendmessage(String action, int[] message) {
		int whichPin = message[0];
		int whichAction = message[1];
		int whichValue = message[2];

		String msg = ARD_BOM + whichPin + ARD_DIVIDER + whichAction + ARD_DIVIDER + whichValue + ARD_EOM;

		System.out.println(msg);

		try {

			if (proxy == null) { //TODO
				proxy = new ProxyOnsDomein();

			}

			proxy.connectClientToServer(requestFromId);
		} catch (IOException e) {
			System.out.println("Geen verbinding met server.");
			return false;

		}
		System.out.println("-> server");
		String response = proxy.sendRequest(action, requestFromId, requestForId, msg);
		proxy.closeConnection();
		System.out.println("Response: " + response);
		

		if (response.equals("No connection with HC")) {
			return false;
		}

		return true;
	}

}
