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
	
//	protected String requestFromId = "123";
//	protected String requestForId = "5678";
	
	

	public GebruikersApplicatie() {

		try { // cluster(s) vanuit bestand laden 
			setClusterList(SerializeHandler.loadClusterList());
		} catch (InvalidClassException | FileNotFoundException | ClassNotFoundException e1) {
			System.out.println("Clusters konden niet geladen worden.");
		}



		// DeviceCommunicator.requestConfigFromServer();   nu via controller in plaats vanuit deze klasse 
		
	}
 
 

	public DeviceCommunicator getDcom() {
		return DeviceCommunicator;
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
