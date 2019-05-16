package controller;

import model.Cluster;
import model.Device;
import view.Main;

import java.util.ArrayList;
import java.util.List;

public class ControllerData {
	
	static protected List deviceList = new ArrayList<Device>();
	static protected List clusterList = new ArrayList<Cluster>();
	
	
	public static void init() {
		
		deviceList = Main.getGa().getDeviceList();
		clusterList = Main.getGa().getClusterList();
		
		
	}
	

}
