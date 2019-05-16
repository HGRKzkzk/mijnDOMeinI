package model;

import jserial.jSerialcomm;
import persistance.SerializeHandler;

import java.io.FileNotFoundException;
import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.List;

public class GebruikersApplicatie {

	private SerializeHandler SerializeHandler = new SerializeHandler();
	private DeviceCommunicator DeviceCommunicator = new DeviceCommunicator(this);

	private List<Device> deviceList = new ArrayList<Device>();
	private List<Cluster> clusterList = new ArrayList<Cluster>();
	private boolean directToArduino = false;

	public GebruikersApplicatie() {

		try {
			// setDeviceList(SerializeHandler.loadDeviceList());
			setClusterList(SerializeHandler.loadClusterList());
		} catch (InvalidClassException | FileNotFoundException | ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println("Direct to Arduino mode is: " + directToArduino);
		if (directToArduino) {

			System.out.println("No data wil be send to server.");
		}
		if (!jSerialcomm.connect() & directToArduino) {
			System.out.println(
					"Arduino not found, no external communication possible. Connect Arduino or change settings.");
		}

		
 
		 DeviceCommunicator.requestConfigFromServer();
		DeviceCommunicator.pushConfigToServer();
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
