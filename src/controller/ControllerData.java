package controller;

import java.util.ArrayList;
import java.util.List;

import model.Cluster;
import model.Device;
import view.Main;

public class ControllerData {
	
	static protected List deviceList = new ArrayList<Device>();
	static protected List clusterList = new ArrayList<Cluster>();
	
	
	public static void init() {

		deviceList = Main.getGa().getDeviceList();
		clusterList = Main.getGa().getClusterList();
		
		
	}
	

}
