package model;

import java.io.FileNotFoundException;
import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.List;


import persistance.SerializeHandler;

public class GebruikersApplicatie {

	private SerializeHandler SerializeHandler = new SerializeHandler();
	private DeviceCommunicator DeviceCommunicator = new DeviceCommunicator(this);
	private List<Device> deviceList = new ArrayList<Device>();
	private List<Cluster> clusterList = new ArrayList<Cluster>();
	private boolean directToArduino = false;
	transient protected String requestFromId = "123";
	transient protected String requestForId = "456";
	
	

	public GebruikersApplicatie() {

		try {
			// setDeviceList(SerializeHandler.loadDeviceList());
			setClusterList(SerializeHandler.loadClusterList());
		} catch (InvalidClassException | FileNotFoundException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}



		DeviceCommunicator.requestConfigFromServer();
		// DeviceCommunicator.pushConfigToServer();
	}
 
 

	public DeviceCommunicator getDcom() {
		return DeviceCommunicator;
	}

	public boolean isDirectToArduino() {
		return directToArduino;
	}

	public void setDirectToArduino(boolean directToArduino) {
		this.directToArduino = directToArduino;
	}

	public List<Cluster> getClusterList() {
		return clusterList;
	}

	public void setClusterList(List<Cluster> clusterList) {
		this.clusterList = clusterList;
	}

	public List<Device> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<Device> deviceList) {
		this.deviceList = deviceList;
	}

}
